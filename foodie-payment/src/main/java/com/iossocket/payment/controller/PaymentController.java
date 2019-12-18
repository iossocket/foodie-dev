package com.iossocket.payment.controller;

import com.iossocket.bo.PaymentRequest;
import com.iossocket.utils.JSONResult;
import com.iossocket.vo.MerchantOrdersVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/order")
    public JSONResult create(@RequestBody MerchantOrdersVO merchantOrdersVO) {
        return JSONResult.success(merchantOrdersVO);
    }

    @PostMapping("pay")
    public JSONResult pay(@RequestBody PaymentRequest paymentRequest) {
        // here you can mock the payment gateway, to notify foodie payment state
        return JSONResult.success("OK");
    }
}
