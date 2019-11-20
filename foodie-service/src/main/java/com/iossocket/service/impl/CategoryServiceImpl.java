package com.iossocket.service.impl;

import com.iossocket.mapper.CategoryMapper;
import com.iossocket.pojo.Category;
import com.iossocket.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> queryCategoryByParentId(Integer parentId) {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("parentId", parentId);
        return categoryMapper.selectByExample(example);
    }
}
