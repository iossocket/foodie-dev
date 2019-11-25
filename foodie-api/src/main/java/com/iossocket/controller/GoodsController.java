package com.iossocket.controller;

import com.iossocket.pojo.Goods;
import com.iossocket.pojo.GoodsDetail;
import com.iossocket.pojo.GoodsImg;
import com.iossocket.pojo.GoodsSpec;
import com.iossocket.service.GoodsService;
import com.iossocket.utils.JSONResult;
import com.iossocket.vo.GoodsInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
