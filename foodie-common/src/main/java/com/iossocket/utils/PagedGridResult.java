package com.iossocket.utils;

import lombok.Data;

import java.util.List;

@Data
public class PagedGridResult {
    private Integer currentPageIndex;
    private Integer totalPageCount;
    private Long totalRecordsCount;
    private List<?> currentPageRows;
}
