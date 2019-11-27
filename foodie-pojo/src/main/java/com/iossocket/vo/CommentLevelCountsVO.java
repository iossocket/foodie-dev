package com.iossocket.vo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CommentLevelCountsVO {
    public Integer totalCounts;
    public Integer goodCounts;
    public Integer normalCounts;
    public Integer badCounts;
}
