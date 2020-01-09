package com.iossocket.controller.center;

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
}
