package com.lzx.dao;

import com.lzx.entity.Order;
import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    Order selectByPrimaryKey(Integer id);

    Order selectByStatus(String status);

    List<Order> selectAll();

    int updateByPrimaryKey(Order record);
}