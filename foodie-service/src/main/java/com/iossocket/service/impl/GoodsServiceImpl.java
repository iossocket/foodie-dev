package com.iossocket.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iossocket.mapper.*;
import com.iossocket.pojo.*;
import com.iossocket.service.GoodsService;
import com.iossocket.utils.DesensitizationUtil;
import com.iossocket.utils.PagedGridResult;
import com.iossocket.vo.CommentLevelCountsVO;
import com.iossocket.vo.GoodsCommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Autowired
    private GoodsCommentsMapper goodsCommentsMapper;

    @Autowired
    private GoodsMapperCustom goodsMapperCustom;

    @Override
    public Goods queryGoodsById(String goodsId) {
        return goodsMapper.selectByPrimaryKey(goodsId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<GoodsImg> queryGoodsImgByGoodsId(String goodsId) {
        Example example = new Example(GoodsImg.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("goodsId", goodsId);
        return goodsImgMapper.selectByExample(example);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<GoodsSpec> queryGoodsSpecListByGoodsId(String goodsId) {
        Example example = new Example(GoodsSpec.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("goodsId", goodsId);
        return goodsSpecMapper.selectByExample(example);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public GoodsDetail queryGoodsDetailByGoodsId(String goodsId) {
        Example example = new Example(GoodsDetail.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("goodsId", goodsId);
        return goodsDetailMapper.selectOneByExample(example);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public CommentLevelCountsVO queryCommentCounts(String goodsId) {
        Example example = new Example(GoodsComments.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("goodsId", goodsId);
        List<GoodsComments> results = goodsCommentsMapper.selectByExample(example);

        int goodCount = 0;
        int normalCount = 0;
        int badCount = 0;
        for (GoodsComments comment: results) {
            if (comment.getCommentLevel() == 1) {
                goodCount += 1;
            } else if (comment.getCommentLevel() == 2) {
                normalCount += 1;
            } else if (comment.getCommentLevel() == 3) {
                badCount += 1;
            }
        }
        CommentLevelCountsVO.CommentLevelCountsVOBuilder builder = CommentLevelCountsVO.builder();
        builder.totalCounts(results.size())
                .goodCounts(goodCount)
                .normalCounts(normalCount)
                .badCounts(badCount);

        return builder.build();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryGoodsComments(String goodsId, Integer level,
                                              Integer currentPageIndex, Integer pageRowCount) {
        Map<String, Object> map = new HashMap<>();
        map.put("goodsId", goodsId);
        map.put("level", level);

        PageHelper.startPage(currentPageIndex, pageRowCount);

        List<GoodsCommentVO> result = goodsMapperCustom.queryGoodsComments(map);

        for (GoodsCommentVO vo : result) {
            vo.setNickName(DesensitizationUtil.commonDisplay(vo.getNickName()));
        }

        return setterPagedGrid(result, currentPageIndex);
    }

    private PagedGridResult setterPagedGrid(List<?> list, Integer currentPageIndex) {
        PageInfo<?> pageList = new PageInfo<>(list);
        PagedGridResult grid = new PagedGridResult();
        grid.setCurrentPageIndex(currentPageIndex);
        grid.setCurrentPageRows(list);
        grid.setTotalPageCount(pageList.getPages());
        grid.setTotalRecordsCount(pageList.getTotal());
        return grid;
    }
}
