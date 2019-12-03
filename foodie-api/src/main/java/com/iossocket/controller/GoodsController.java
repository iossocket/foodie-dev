package com.iossocket.controller;

import com.iossocket.pojo.Goods;
import com.iossocket.pojo.GoodsDetail;
import com.iossocket.pojo.GoodsImg;
import com.iossocket.pojo.GoodsSpec;
import com.iossocket.service.GoodsService;
import com.iossocket.utils.JSONResult;
import com.iossocket.utils.PagedGridResult;
import com.iossocket.vo.CommentLevelCountsVO;
import com.iossocket.vo.GoodsInfoVO;
import com.iossocket.vo.ShoppingCartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping("/info/{goodsId}")
    public JSONResult getGoodsInfo(@PathVariable String goodsId) {
        Goods goods = goodsService.queryGoodsById(goodsId);
        List<GoodsImg> goodsImgList = goodsService.queryGoodsImgByGoodsId(goodsId);
        List<GoodsSpec> goodsSpecList = goodsService.queryGoodsSpecListByGoodsId(goodsId);
        GoodsDetail goodsDetail = goodsService.queryGoodsDetailByGoodsId(goodsId);

        GoodsInfoVO.GoodsInfoVOBuilder builder = GoodsInfoVO.builder();
        builder
                .goods(goods)
                .goodsImgList(goodsImgList)
                .goodsSpecList(goodsSpecList)
                .goodsDetail(goodsDetail);

        return JSONResult.success(builder.build());
    }

    @GetMapping("/commentCount")
    public JSONResult getGoodsCommentCount(@RequestParam String goodsId) {
        CommentLevelCountsVO result = goodsService.queryCommentCounts(goodsId);
        return JSONResult.success(result);
    }

    @GetMapping("/comments")
    public JSONResult getGoodsComments(@RequestParam String goodsId,
                                       @RequestParam Integer level,
                                       @RequestParam Integer currentPageIndex,
                                       @RequestParam Integer pageSize) {
        PagedGridResult result = goodsService.queryGoodsComments(goodsId, level, currentPageIndex, pageSize);
        return JSONResult.success(result);
    }

    @GetMapping("/search")
    public JSONResult searchGoods(@RequestParam String keyword,
                                  @RequestParam String sort,
                                  @RequestParam Integer currentPageIndex,
                                  @RequestParam Integer pageSize) {
        PagedGridResult result = goodsService.searchGoods(keyword, sort, currentPageIndex, pageSize);
        return JSONResult.success(result);
    }

    @GetMapping("/goods")
    public JSONResult queryGoodsByCategory(@RequestParam String categoryId,
                                           @RequestParam String sort,
                                           @RequestParam Integer currentPageIndex,
                                           @RequestParam Integer pageSize) {
        PagedGridResult result = goodsService.queryGoodsByCategory(categoryId, sort, currentPageIndex, pageSize);
        return JSONResult.success(result);
    }

    @GetMapping("/shoppingcart")
    public JSONResult queryGoodsBySpecIds(@RequestParam String specIdListString) {
        String[] ids = specIdListString.split(",");
        ArrayList<String> specIds = new ArrayList<>();
        Collections.addAll(specIds, ids);
        List<ShoppingCartVO> result = goodsService.queryGoodsBySpecIds(specIds);
        return JSONResult.success(result);
    }
}
