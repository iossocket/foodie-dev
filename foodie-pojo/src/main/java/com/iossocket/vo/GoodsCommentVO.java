package com.iossocket.vo;

import lombok.Data;

import java.util.Date;

@Data
public class GoodsCommentVO {
    private String nickName;
    private String avatar;
    private Integer commentLevel;
    private String content;
    private String specName;
    private Date createdTime;
}
