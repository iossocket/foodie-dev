package com.iossocket.service.center;

import com.iossocket.utils.PagedGridResult;

public interface MyOrderService {
    PagedGridResult queryMyOrders(String userId, Integer orderStatus, Integer page, Integer size);
}
