package com.iossocket.vo;

import lombok.Data;

import java.util.List;

@Data
public class LatestGoodsVO {
    private Integer rootCategoryId;
    private String rootCategoryName;
    private String description;
    private String categoryImage;
    private String bgColor;

    private List<SimpleGoodsVO> simpleGoodsList;
}
