package com.iossocket.vo;

import lombok.Data;

import java.util.List;

@Data
public class CategoryVO {
    private Integer id;
    private String name;
    private String type;
    private Integer parentId;

    // 三级分类vo list
    private List<SubCategoryVO> subCategoryList;
}
