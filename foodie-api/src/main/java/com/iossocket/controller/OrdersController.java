package com.iossocket.controller;

import com.iossocket.bo.SubmitOrderBO;
import com.iossocket.service.OrderService;
import com.iossocket.utils.JSONResult;
import com.iossocket.vo.MerchantOrdersVO;
import com.iossocket.vo.OrderVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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

    @Autowired
    private RestTemplate restTemplate;

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
        MerchantOrdersVO merchantOrdersVO = orderVO.getMerchantOrdersVO();
        String orderId = orderVO.getOrderId();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MerchantOrdersVO> entity = new HttpEntity<>(merchantOrdersVO, headers);
        ResponseEntity<JSONResult> responseEntity =
                restTemplate.postForEntity("http://localhost:8099/payment/order",
                        entity, JSONResult.class);
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            JSONResult orderResponse = responseEntity.getBody();
            return JSONResult.error(orderResponse.getData());
        }

        return JSONResult.success(orderId);
    }
}
