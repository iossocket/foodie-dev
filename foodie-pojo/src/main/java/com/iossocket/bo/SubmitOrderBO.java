package com.iossocket.bo;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class SubmitOrderBO {

    @NotEmpty(message = "user id cannot be empty")
    private String userId;
    @NotEmpty(message = "order cannot be empty")
    private String goodsSpecIds;
    @NotEmpty(message = "address cannot be empty")
    private String addressId;

    @Max(value = 2, message = "invalid pay way")
    @Min(value = 1, message = "invalid pay way")
    @NotNull(message = "should choose the pay way")
    private Integer payMethod;
    private String leftMsg;
}
