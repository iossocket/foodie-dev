package com.iossocket.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "order_detail")
public class OrderDetail {
    /**
     * 主键id
     */
    @Id
    private String id;

    /**
     * 归属订单id
     */
    @Column(name = "order_id")
    private String orderId;

    /**
     * 商品id
     */
    @Column(name = "goods_id")
    private String goodsId;

    /**
     * 商品图片
     */
    @Column(name = "goods_main_img")
    private String goodsMainImg;

    /**
     * 商品名称
     */
    @Column(name = "goods_name")
    private String goodsName;

    /**
     * 规格id
     */
    @Column(name = "goods_spec_id")
    private String goodsSpecId;

    /**
     * 规格名称
     */
    @Column(name = "goods_spec_name")
    private String goodsSpecName;

    /**
     * 成交价格
     */
    private Integer price;

    /**
     * 购买数量
     */
    private Integer quantity;
}