package com.iossocket.vo;

import lombok.Data;

@Data
public class SubCategoryVO {
    private Integer subId;
    private String subName;
    private String subType;
    private Integer subParentId;
}
