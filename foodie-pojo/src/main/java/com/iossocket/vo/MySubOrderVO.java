package com.iossocket.vo;

import lombok.Data;

@Data
public class MySubOrderVO {
    private String goodsId;
    private String goodsName;
    private String goodsImg;
    private String goodsSpecId;
    private String goodsSpecName;
    private Integer quantity;
    private Integer price;
}
