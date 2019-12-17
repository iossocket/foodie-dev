package com.iossocket.service.impl;

import com.iossocket.bo.SubmitOrderBO;
import com.iossocket.enums.OrderStatusEnum;
import com.iossocket.mapper.OrderDetailMapper;
import com.iossocket.mapper.OrderStatusMapper;
import com.iossocket.mapper.OrdersMapper;
import com.iossocket.pojo.*;
import com.iossocket.service.AddressService;
import com.iossocket.service.GoodsService;
import com.iossocket.service.OrderService;
import com.iossocket.utils.DateUtil;
import com.iossocket.vo.MerchantOrdersVO;
import com.iossocket.vo.OrderVO;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Autowired
    private AddressService addressService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public OrderVO createOrder(SubmitOrderBO submitOrderBO) {

        String userId = submitOrderBO.getUserId();
        String addressId = submitOrderBO.getAddressId();
        Integer payMethod = submitOrderBO.getPayMethod();
        String leftMsg = submitOrderBO.getLeftMsg();
        String goodsSpecIds = submitOrderBO.getGoodsSpecIds();
        UserAddress address = addressService.queryAddress(userId, addressId);

        Integer postFees = 0;

        // 1. 新订单数据保存
        String orderId = sid.nextShort();
        Orders newOrder = new Orders();
        newOrder.setId(orderId);
        newOrder.setUserId(userId);
        newOrder.setReceiverName(address.getReceiver());
        newOrder.setReceiverMobile(address.getMobile());
        newOrder.setReceiverAddress(
                address.getProvince() + " "
                        + address.getCity() + " "
                        + address.getDistrict() + " "
                        + address.getDetail());
        newOrder.setPostFees(postFees);
        newOrder.setPayMethod(payMethod);
        newOrder.setLeftMsg(leftMsg);
        newOrder.setIsComment(0);
        newOrder.setIsDelete(0);
        Date date = new Date();
        newOrder.setCreatedTime(date);
        newOrder.setUpdatedTime(date);

        // 2. 循环根据itemSpecIds保存订单商品信息表
        String goodsSpecIdArr[] = goodsSpecIds.split(",");
        Integer totalOriginPrice = 0;
        Integer totalDiscountPrice = 0;

        for (String specId : goodsSpecIdArr) {
            // TODO 整合redis后，商品购买的数量重新从redis的购物车中获取

            int buyCounts = 1;

            // 2.1 根据规格id，查询规格的具体信息，主要获取价格
            GoodsSpec goodsSpec = goodsService.queryGoodsSpecById(specId);
            totalOriginPrice += goodsSpec.getOriginPrice() * buyCounts;
            totalDiscountPrice += goodsSpec.getDiscountPrice() * buyCounts;

            // 2.2 根据商品id，获得商品信息以及商品图片
            String goodsId = goodsSpec.getGoodsId();
            String imgUrl = goodsService.queryGoodsMainImgById(goodsId);
            Goods goods = goodsService.queryGoodsById(goodsId);
            // 2.3 循环保存子订单数据到数据库
            String orderDetailId = sid.nextShort();
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setId(orderDetailId);
            orderDetail.setOrderId(orderId);
            orderDetail.setGoodsId(goodsId);
            orderDetail.setGoodsName(goods.getName());
            orderDetail.setGoodsMainImg(imgUrl);
            orderDetail.setQuantity(buyCounts);
            orderDetail.setGoodsSpecId(specId);
            orderDetail.setGoodsSpecName(goodsSpec.getName());
            orderDetail.setPrice(goodsSpec.getDiscountPrice());
            orderDetailMapper.insert(orderDetail);

            // 2.4 在用户提交订单以后，规格表中需要扣除库存
            goodsService.decreaseGoodsSpecStock(specId, buyCounts, date);
        }

        newOrder.setTotalPrice(totalOriginPrice);
        newOrder.setFinalPrice(totalDiscountPrice);
        ordersMapper.insert(newOrder);

        // 3. 保存订单状态表
        OrderStatus waitPayOrderStatus = new OrderStatus();
        waitPayOrderStatus.setOrderId(orderId);
        waitPayOrderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        waitPayOrderStatus.setCreatedTime(new Date());
        orderStatusMapper.insert(waitPayOrderStatus);

        // 4. 构建商户订单，用于传给支付中心
        MerchantOrdersVO merchantOrdersVO = new MerchantOrdersVO();
        merchantOrdersVO.setMerchantOrderId(orderId);
        merchantOrdersVO.setMerchantUserId(userId);
        merchantOrdersVO.setAmount(totalDiscountPrice + postFees);
        merchantOrdersVO.setPayMethod(payMethod);

        // 5. 构建自定义订单vo
        OrderVO orderVO = new OrderVO();
        orderVO.setOrderId(orderId);
        orderVO.setMerchantOrdersVO(merchantOrdersVO);

        return orderVO;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateOrderStatus(String orderId, Integer orderStatus) {
        OrderStatus newStatus = new OrderStatus();
        newStatus.setOrderId(orderId);
        newStatus.setOrderStatus(orderStatus);
        newStatus.setPayTime(new Date());

        orderStatusMapper.updateByPrimaryKeySelective(newStatus);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public OrderStatus queryOrderStatusInfo(String orderId) {
        return orderStatusMapper.selectByPrimaryKey(orderId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void closeOrder() {
        // 查询所有未付款订单，判断时间是否超时（1天），超时则关闭交易
        OrderStatus queryOrder = new OrderStatus();
        queryOrder.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        List<OrderStatus> orderStatusList = orderStatusMapper.select(queryOrder);
        Date now = new Date();
        for (OrderStatus orderStatus : orderStatusList) {
            Date createdTime = orderStatus.getCreatedTime();
            int days = DateUtil.daysBetween(createdTime, now);
            if (days >= 1) {
                doCloseOrder(orderStatus.getOrderId());
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    void doCloseOrder(String orderId) {
        OrderStatus close = new OrderStatus();
        close.setOrderId(orderId);
        close.setOrderStatus(OrderStatusEnum.CLOSE.type);
        close.setCloseTime(new Date());
        orderStatusMapper.updateByPrimaryKeySelective(close);
    }
}
