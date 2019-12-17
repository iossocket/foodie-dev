package com.iossocket.mapper;

import com.iossocket.vo.GoodsCommentVO;
import com.iossocket.vo.SearchGoodsVO;
import com.iossocket.vo.ShoppingCartVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface GoodsMapperCustom {
    List<GoodsCommentVO> queryGoodsComments(@Param("paramsMap") Map<String, Object> map);
    List<SearchGoodsVO> searchGoods(@Param("paramsMap") Map<String, Object> map);
    List<SearchGoodsVO> queryGoodsByThirdCategory(@Param("paramsMap") Map<String, Object> map);
    List<ShoppingCartVO> queryGoodsBySpecIds(@Param("paramsList") List specIdList);
    int decreaseGoodsSpecStock(@Param("specId") String specId,
                               @Param("pendingCounts") int pendingCounts,
                               @Param("updatedTime") Date date);
}
