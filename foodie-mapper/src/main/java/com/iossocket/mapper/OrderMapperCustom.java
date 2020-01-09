package com.iossocket.mapper;

import com.iossocket.vo.MyOrdersVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrderMapperCustom {
    List<MyOrdersVO> queryMyOrders(@Param("paramsMap") Map<String, Object> map);
}
