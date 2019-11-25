package com.iossocket.service.impl;

import com.iossocket.mapper.GoodsDetailMapper;
import com.iossocket.mapper.GoodsImgMapper;
import com.iossocket.mapper.GoodsMapper;
import com.iossocket.mapper.GoodsSpecMapper;
import com.iossocket.pojo.Goods;
import com.iossocket.pojo.GoodsDetail;
import com.iossocket.pojo.GoodsImg;
import com.iossocket.pojo.GoodsSpec;
import com.iossocket.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsImgMapper goodsImgMapper;

    @Autowired
    private GoodsSpecMapper goodsSpecMapper;

    @Autowired
    private GoodsDetailMapper goodsDetailMapper;

    @Override
    public Goods queryGoodsById(String goodsId) {
        return goodsMapper.selectByPrimaryKey(goodsId);
    }

    @Override
    public List<GoodsImg> queryGoodsImgByGoodsId(String goodsId) {
        Example example = new Example(GoodsImg.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("goodsId", goodsId);
        return goodsImgMapper.selectByExample(example);
    }

    @Override
    public List<GoodsSpec> queryGoodsSpecListByGoodsId(String goodsId) {
        Example example = new Example(GoodsSpec.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("goodsId", goodsId);
        return goodsSpecMapper.selectByExample(example);
    }

    @Override
    public GoodsDetail queryGoodsDetailByGoodsId(String goodsId) {
        Example example = new Example(GoodsDetail.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("goodsId", goodsId);
        return goodsDetailMapper.selectOneByExample(example);
    }
}
