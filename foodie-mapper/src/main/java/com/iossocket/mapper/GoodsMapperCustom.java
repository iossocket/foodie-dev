package com.iossocket.mapper;

import com.iossocket.vo.GoodsCommentVO;
import com.iossocket.vo.SearchGoodsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface GoodsMapperCustom {
    List<GoodsCommentVO> queryGoodsComments(@Param("paramsMap") Map<String, Object> map);
    List<SearchGoodsVO> searchGoods(@Param("paramsMap") Map<String, Object> map);
}
