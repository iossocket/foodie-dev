package com.iossocket.service.impl.center;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iossocket.enums.OrderStatusEnum;
import com.iossocket.mapper.OrderMapperCustom;
import com.iossocket.mapper.OrderStatusMapper;
import com.iossocket.mapper.OrdersMapper;
import com.iossocket.pojo.OrderStatus;
import com.iossocket.pojo.Orders;
import com.iossocket.service.center.MyOrderService;
import com.iossocket.utils.PagedGridResult;
import com.iossocket.vo.MyOrdersVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MyOrderServiceImpl implements MyOrderService {

    @Autowired
    private OrderMapperCustom orderMapperCustom;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryMyOrders(String userId, Integer orderStatus, Integer page, Integer size) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        if (orderStatus != null) {
            map.put("orderStatus", orderStatus);
        }
        PageHelper.startPage(page, size);
        List<MyOrdersVO> myOrdersVOList = orderMapperCustom.queryMyOrders(map);
        return setPagedGrid(myOrdersVOList, page);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Orders updateOrderStatus(String userId, String orderId, OrderStatusEnum newStatus) {
        Example example = new Example(OrderStatus.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId", orderId);
        List<OrderStatus> orderStatuses = orderStatusMapper.selectByExample(example);
        if (orderStatuses == null || orderStatuses.size() == 0) {
            return null;
        }

        OrderStatus orderStatus = updateOrderStatus(orderStatuses.get(0), newStatus);
        if (orderStatus == null) {
            return null;
        }

        Orders orders = ordersMapper.selectByPrimaryKey(orderStatus.getOrderId());
        if (orderStatuses.get(0).getOrderStatus().equals(newStatus.type)) {
            return orders;
        }
        orderStatusMapper.updateByPrimaryKeySelective(orderStatus);
        return orders;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Integer deleteOrder(String userId, String orderId) {
        Orders orders = ordersMapper.selectByPrimaryKey(orderId);
        if (orders == null || !orders.getUserId().equals(userId)) {
            return null;
        }
        orders.setIsDelete(1);
        return ordersMapper.updateByPrimaryKeySelective(orders);
    }

    private PagedGridResult setPagedGrid(List<?> list, Integer page) {
        PageInfo<?> pageList = new PageInfo<>(list);
        PagedGridResult grid = new PagedGridResult();
        grid.setCurrentPageIndex(page);
        grid.setCurrentPageRows(list);
        grid.setTotalPageCount(pageList.getPages());
        grid.setTotalRecordsCount(pageList.getTotal());
        return grid;
    }

    private OrderStatus updateOrderStatus(OrderStatus orderStatus, OrderStatusEnum newStatus) {
        Integer prevStatus = orderStatus.getOrderStatus();
        if (prevStatus > newStatus.type) {
            return null;
        }
        if (prevStatus.equals(OrderStatusEnum.WAIT_PAY.type) && !newStatus.type.equals(OrderStatusEnum.WAIT_DELIVER.type)) {
            return null;
        }
        orderStatus.setOrderStatus(newStatus.type);
        if (newStatus.type.equals(OrderStatusEnum.WAIT_DELIVER.type)) {
            orderStatus.setPayTime(new Date());
        } else if (newStatus.type.equals(OrderStatusEnum.WAIT_RECEIVE.type)) {
            orderStatus.setDeliverTime(new Date());
        } else if (newStatus.type.equals(OrderStatusEnum.SUCCESS.type)) {
            orderStatus.setSuccessTime(new Date());
        } else if (newStatus.type.equals(OrderStatusEnum.CLOSE.type)) {
            orderStatus.setCloseTime(new Date());
        }
        return orderStatus;
    }
}
