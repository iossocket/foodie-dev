package com.iossocket.service;

import com.iossocket.pojo.Carousel;

import java.util.List;

public interface CarouselService {
    List<Carousel> queryAll(Integer shouldDisplay);
}
