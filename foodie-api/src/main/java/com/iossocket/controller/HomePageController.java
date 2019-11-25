package com.iossocket.controller;

import com.iossocket.pojo.Carousel;
import com.iossocket.pojo.Category;
import com.iossocket.service.CarouselService;
import com.iossocket.service.CategoryService;
import com.iossocket.utils.JSONResult;
import com.iossocket.vo.CategoryVO;
import com.iossocket.vo.LatestGoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/subcategory/{rootId}")
    public JSONResult getAllSubCategoryByRootId(@PathVariable Integer rootId) {
        List<CategoryVO> result = categoryService.queryAllSubCategoryByRootId(rootId);
        return JSONResult.success(result);
    }

    @GetMapping("/latestgoods")
    public JSONResult getLatestGoodsByRootId(@RequestParam Integer rootId) {
        LatestGoodsVO result = categoryService.queryLatestGoodsByRootId(rootId);
        return JSONResult.success(result);
    }

}
