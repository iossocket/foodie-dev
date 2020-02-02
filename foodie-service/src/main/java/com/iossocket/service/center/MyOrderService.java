package com.iossocket.service.center;

import com.iossocket.enums.OrderStatusEnum;
import com.iossocket.pojo.Orders;
import com.iossocket.utils.PagedGridResult;

public interface MyOrderService {
    PagedGridResult queryMyOrders(String userId, Integer orderStatus, Integer page, Integer size);
    Orders updateOrderStatus(String userId, String orderId, OrderStatusEnum newStatus);
    Integer deleteOrder(String userId, String orderId);
}
