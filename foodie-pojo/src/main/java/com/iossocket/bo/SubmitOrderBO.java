package com.iossocket.bo;

import lombok.Data;

@Data
public class SubmitOrderBO {
    private String userId;
    private String goodsSpecIds;
    private String addressId;
    private Integer payMethod;
    private String leftMsg;
}
