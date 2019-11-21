package com.iossocket.service;

import com.iossocket.pojo.Category;
import com.iossocket.vo.CategoryVO;

import java.util.List;

public interface CategoryService {
    List<Category> queryCategoryByParentId(Integer parentId);
    List<CategoryVO> queryAllSubCategoryByRootId(Integer rootId);
}
