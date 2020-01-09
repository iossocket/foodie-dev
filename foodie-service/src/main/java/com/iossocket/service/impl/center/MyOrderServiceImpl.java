package com.iossocket.service.impl.center;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iossocket.mapper.OrderMapperCustom;
import com.iossocket.service.center.MyOrderService;
import com.iossocket.utils.PagedGridResult;
import com.iossocket.vo.MyOrdersVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MyOrderServiceImpl implements MyOrderService {

    @Autowired
    private OrderMapperCustom orderMapperCustom;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryMyOrders(String userId, Integer orderStatus, Integer page, Integer size) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        if (orderStatus != null) {
            map.put("orderStatus", orderStatus);
        }
        PageHelper.startPage(page, size);
        List<MyOrdersVO> myOrdersVOList = orderMapperCustom.queryMyOrders(map);
        return setPagedGrid(myOrdersVOList, page);
    }

    private PagedGridResult setPagedGrid(List<?> list, Integer page) {
        PageInfo<?> pageList = new PageInfo<>(list);
        PagedGridResult grid = new PagedGridResult();
        grid.setCurrentPageIndex(page);
        grid.setCurrentPageRows(list);
        grid.setTotalPageCount(pageList.getPages());
        grid.setTotalRecordsCount(pageList.getTotal());
        return grid;
    }
}
