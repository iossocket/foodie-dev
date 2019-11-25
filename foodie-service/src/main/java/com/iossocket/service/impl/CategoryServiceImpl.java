package com.iossocket.service.impl;

import com.iossocket.mapper.CategoryMapper;
import com.iossocket.mapper.CategoryMapperCustom;
import com.iossocket.pojo.Category;
import com.iossocket.service.CategoryService;
import com.iossocket.vo.CategoryVO;
import com.iossocket.vo.LatestGoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryMapperCustom categoryMapperCustom;

    @Override
    public List<Category> queryCategoryByParentId(Integer parentId) {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("parentId", parentId);
        return categoryMapper.selectByExample(example);
    }

    @Override
    public List<CategoryVO> queryAllSubCategoryByRootId(Integer rootId) {
        List<CategoryVO> result = categoryMapperCustom.getSubCategoryList(rootId);
        return result;
    }

    @Override
    public LatestGoodsVO queryLatestGoodsByRootId(Integer rootId) {
        Map<String, Object> map = new HashMap<>();
        map.put("rootCategoryId", rootId);
        return categoryMapperCustom.getLatestGoodsList(map);
    }
}
