package com.iossocket.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@Data
public class Goods {
    /**
     * 商品主键id
     */
    @Id
    private String id;

    /**
     * 商品名称 商品名称
     */
    private String name;

    /**
     * 分类外键id 分类id
     */
    @Column(name = "category_id")
    private Integer categoryId;

    /**
     * 一级分类外键id
     */
    @Column(name = "root_category_id")
    private Integer rootCategoryId;

    /**
     * 累计销售 累计销售
     */
    private Integer sales;

    /**
     * 上下架状态 上下架状态,1:上架 2:下架
     */
    private Integer status;

    /**
     * 创建时间
     */
    @Column(name = "created_time")
    private Date createdTime;

    /**
     * 更新时间
     */
    @Column(name = "updated_time")
    private Date updatedTime;

    /**
     * 商品内容 商品内容
     */
    private String description;
}