package com.iossocket.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MyOrdersVO {
    private String orderId;
    private Date createdTime;
    private Integer payMethod;
    private Integer finalPrice;
    private Integer postFees;
    private Integer orderStatus;
    private Integer isComment;

    private List<MySubOrderVO> mySubOrderVOList;
}
