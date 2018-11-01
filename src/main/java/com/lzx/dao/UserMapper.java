package com.lzx.dao;

import com.lzx.entity.User;
import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);
    int deleteByUserName(String userName);

    int insert(User record);

    User selectByPrimaryKey(Integer id);

    User selectByUserName(String userName);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

    int logIn(User user);

    int updateStatus(User user);
}