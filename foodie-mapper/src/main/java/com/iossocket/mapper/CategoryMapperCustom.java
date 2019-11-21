package com.iossocket.mapper;

import com.iossocket.vo.CategoryVO;

import java.util.List;

public interface CategoryMapperCustom {
    List<CategoryVO> getSubCategoryList(Integer rootCategoryId);
}
