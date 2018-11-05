package com.lzx.dao;

import com.lzx.entity.Pet;
import com.lzx.test.BaseSpringTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PetMapperTest extends BaseSpringTest {

    @Autowired
    PetMapper petMapper;

    @Test
    public void deleteByPrimaryKey() {
        petMapper.deleteByPrimaryKey(4);
    }

    @Test
    public void insert() {
        petMapper.insert(new Pet(1,"小黄",1,"available"));
    }

    @Test
    public void selectByPrimaryKey() {
        System.out.println(petMapper.selectByPrimaryKey(2).toString());
    }

    @Test
    public void selectByStatus() {
        petMapper.selectByStatus("available");
    }

    @Test
    public void selectAll() {
       petMapper.selectAll();
    }


    @Test
    public void upLoadImg() {
        petMapper.upLoadImg(new Pet(7,"img/2.jpg"));
    }

    public void updateByPrimaryKey(){
        petMapper.updateByPrimaryKey(new Pet(7,2,"中黄",1,"available"));
    }

}