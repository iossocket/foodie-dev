package com.iossocket.vo;

import com.iossocket.pojo.Goods;
import com.iossocket.pojo.GoodsDetail;
import com.iossocket.pojo.GoodsImg;
import com.iossocket.pojo.GoodsSpec;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GoodsInfoVO {
    private Goods goods;
    private List<GoodsImg> goodsImgList;
    private List<GoodsSpec> goodsSpecList;
    private GoodsDetail goodsDetail;
}
