package com.lzx.dao;

import com.lzx.entity.Order;

import java.util.HashMap;
import java.util.List;

public interface OrderMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    Order selectByPrimaryKey(Integer id);

    HashMap<String, Integer> countByStatus();

    List<Order> selectAll();

    int updateByPrimaryKey(Order record);
}