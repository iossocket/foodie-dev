package com.iossocket.service.impl;

import com.iossocket.mapper.CarouselMapper;
import com.iossocket.pojo.Carousel;
import com.iossocket.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class CarouselServiceImpl implements CarouselService {

    @Autowired
    private CarouselMapper carouselMapper;

    @Override
    public List<Carousel> queryAll(Integer shouldDisplay) {
        Example example = new Example(Carousel.class);
        example.orderBy("orderIndex").asc();
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("shouldDisplay", shouldDisplay);
        return carouselMapper.selectByExample(example);
    }
}
