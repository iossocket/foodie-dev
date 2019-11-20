package com.iossocket.service;

import com.iossocket.pojo.Category;

import java.util.List;

public interface CategoryService {
    List<Category> queryCategoryByParentId(Integer parentId);
}
