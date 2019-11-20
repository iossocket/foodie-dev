package com.iossocket.controller;

import com.iossocket.pojo.Carousel;
import com.iossocket.pojo.Category;
import com.iossocket.service.CarouselService;
import com.iossocket.service.CategoryService;
import com.iossocket.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RequestMapping("/home")
@RestController
public class HomePageController {

    @Autowired
    private CarouselService carouselService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/carousel")
    public JSONResult getCarousel() {
        List<Carousel> list = carouselService.queryAll(1);
        return JSONResult.success(list);
    }

    @GetMapping("/category/{level}")
    public JSONResult getCategoryByLevel(@PathVariable Integer level) {
        List<Category> result = categoryService.queryCategoryByParentId(level);
        return JSONResult.success(result);
    }
}
