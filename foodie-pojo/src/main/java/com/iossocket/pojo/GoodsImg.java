package com.iossocket.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "goods_img")
public class GoodsImg {
    /**
     * 图片主键
     */
    @Id
    private String id;

    /**
     * 商品外键id 商品外键id
     */
    @Column(name = "goods_id")
    private String goodsId;

    /**
     * 图片地址 图片地址
     */
    private String url;

    /**
     * 顺序 图片顺序，从小到大
     */
    @Column(name = "order_index")
    private Integer orderIndex;

    /**
     * 是否主图 是否主图，1：是，0：否
     */
    @Column(name = "is_main")
    private Integer isMain;

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