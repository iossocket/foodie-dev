package com.iossocket.controller.center;

import com.iossocket.enums.OrderStatusEnum;
import com.iossocket.pojo.Orders;
import com.iossocket.service.center.MyOrderService;
import com.iossocket.utils.JSONResult;
import com.iossocket.utils.PagedGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/center")
public class MyOrderController {
    @Autowired
    private MyOrderService myOrderService;

    @GetMapping("/user/{id}/orders")
    public JSONResult queryMyOrders(@PathVariable("id") String userId,
                                    @RequestParam("pageIndex") Integer index,
                                    @RequestParam("pageSize") Integer size) {
        PagedGridResult result = myOrderService.queryMyOrders(userId, null, index, size);
        return JSONResult.success(result);
    }

    @PutMapping("/user/{id}/orders")
    public JSONResult updateOrderStatusToReceived(@PathVariable("id") String userId,
                                                  @RequestBody String orderId) {
        Orders order = myOrderService.updateOrderStatus(userId, orderId, OrderStatusEnum.SUCCESS);
        return JSONResult.success(order);
    }

    @DeleteMapping("/user/{userId}/orders/{orderId}")
    public JSONResult deleteOrder(@PathVariable("userId") String userId,
                                  @PathVariable("orderId") String orderId) {
        Integer result = myOrderService.deleteOrder(userId, orderId);
        return JSONResult.success(result == 1);
    }
}
