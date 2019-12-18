package com.iossocket.bo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PaymentRequest {
    @NotEmpty
    private String userId;
    @NotEmpty
    private String orderId;
}
