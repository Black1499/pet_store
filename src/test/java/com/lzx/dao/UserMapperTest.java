package com.lzx.dao;

import com.lzx.entity.User;
import com.lzx.test.BaseSpringTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserMapperTest extends BaseSpringTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void addUser(){
        userMapper.insert(new User("田期","田","期","asddf@112.com","asdsfaf","12312312311",0));
    }

    @Test
    public void selectAll(){
        userMapper.selectAll();
    }

    @Test
    public void findByUserName(){
        userMapper.selectByUserName("田期");
    }
    @Test
    public void deleteByUserName(){
        userMapper.deleteByUserName("田期");
    }
    @Test
    public void updateByUserName(){
        userMapper.updateByUserName(new User("田期","田","期","asddf@112.com","12312312311","12312312311",0));
    }
    @Test
    public void login(){
        userMapper.selectByNameAndPassword(new User("田期","asdsfaf"));
        userMapper.updateStatus(new User("田期",0));
    }
    @Test
    public void logout(){
        userMapper.updateByUserName(new User("田期",1));
    }
}
