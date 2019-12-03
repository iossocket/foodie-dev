package com.iossocket.vo;

import lombok.Data;

@Data
public class ShoppingCartVO {
    private String goodsId;
    private String goodsImgUrl;
    private String goodsName;
    private String specId;
    private String specName;
    private String discountPrice;
    private String originPrice;
}
