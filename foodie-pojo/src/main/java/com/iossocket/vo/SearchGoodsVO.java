package com.iossocket.vo;

import lombok.Data;

@Data
public class SearchGoodsVO {
    private String goodsId;
    private String goodsName;
    private Integer sellCounts;
    private String imgUrl;
    private Integer price;
}
