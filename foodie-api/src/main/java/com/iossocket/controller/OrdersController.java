package com.iossocket.controller;

import com.iossocket.bo.SubmitOrderBO;
import com.iossocket.service.OrderService;
import com.iossocket.utils.JSONResult;
import com.iossocket.vo.OrderVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/orders")
@RestController
public class OrdersController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public JSONResult createOrder(@Valid @RequestBody SubmitOrderBO submitOrderBO,
                                  BindingResult bindingResult) {
        log.info("orders: {}", submitOrderBO);
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            Map<String, String> errorObj = new HashMap<>();
            for (ObjectError error : errors) {
                errorObj.put(((FieldError)error).getField(), error.getDefaultMessage());
            }
            log.info("create order error: {}", errorObj);
            return JSONResult.error(errorObj);
        }

        OrderVO orderVO = orderService.createOrder(submitOrderBO);
        String orderId = orderVO.getOrderId();

        return JSONResult.success(orderId);
    }
}
