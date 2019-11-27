package com.iossocket.service;

import com.iossocket.pojo.Goods;
import com.iossocket.pojo.GoodsDetail;
import com.iossocket.pojo.GoodsImg;
import com.iossocket.pojo.GoodsSpec;
import com.iossocket.utils.PagedGridResult;
import com.iossocket.vo.CommentLevelCountsVO;

import java.util.List;

public interface GoodsService {
    Goods queryGoodsById(String goodsId);
    List<GoodsImg> queryGoodsImgByGoodsId(String goodsId);
    List<GoodsSpec> queryGoodsSpecListByGoodsId(String goodsId);
    GoodsDetail queryGoodsDetailByGoodsId(String goodsId);
    CommentLevelCountsVO queryCommentCounts(String goodsId);
    PagedGridResult queryGoodsComments(String goodsId, Integer level, Integer currentPageIndex, Integer pageRowCount);
    PagedGridResult searchGoods(String keyword, String sort, Integer currentPageIndex, Integer pageRowCount);
    PagedGridResult queryGoodsByCategory(String categoryId, String sort, Integer currentPageIndex, Integer pageRowCount);
}
