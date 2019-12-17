package com.iossocket.service;

import com.iossocket.bo.SubmitOrderBO;
import com.iossocket.pojo.OrderStatus;
import com.iossocket.vo.OrderVO;

public interface OrderService {
    public OrderVO createOrder(SubmitOrderBO submitOrderBO);
    public void updateOrderStatus(String orderId, Integer orderStatus);
    public OrderStatus queryOrderStatusInfo(String orderId);
    public void closeOrder();
}
