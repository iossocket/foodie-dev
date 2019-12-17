package com.iossocket.service;

import com.iossocket.bo.SubmitOrderBO;
import com.iossocket.pojo.OrderStatus;
import com.iossocket.vo.OrderVO;

public interface OrderService {
    OrderVO createOrder(SubmitOrderBO submitOrderBO);
    void updateOrderStatus(String orderId, Integer orderStatus);
    OrderStatus queryOrderStatusInfo(String orderId);
    void closeOrder();
}
