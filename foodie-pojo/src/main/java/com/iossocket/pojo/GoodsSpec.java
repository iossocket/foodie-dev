package com.iossocket.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Table(name = "goods_spec")
public class GoodsSpec {
    /**
     * 商品规格id
     */
    @Id
    private String id;

    /**
     * 商品外键id
     */
    @Column(name = "goods_id")
    private String goodsId;

    /**
     * 规格名称
     */
    private String name;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 折扣力度
     */
    @Column(name = "discount_rate")
    private BigDecimal discountRate;

    /**
     * 优惠价
     */
    @Column(name = "discount_price")
    private Integer discountPrice;

    /**
     * 原价
     */
    @Column(name = "origin_price")
    private Integer originPrice;

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
}