package com.iossocket.mapper;

import com.iossocket.vo.CategoryVO;
import com.iossocket.vo.LatestGoodsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CategoryMapperCustom {
    List<CategoryVO> getSubCategoryList(Integer rootCategoryId);
    LatestGoodsVO getLatestGoodsList(@Param("paramsMap") Map<String, Object> map);
}
