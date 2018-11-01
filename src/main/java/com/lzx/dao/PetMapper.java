package com.lzx.dao;

import com.lzx.entity.Pet;
import java.util.List;

public interface PetMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Pet record);

    Pet selectByPrimaryKey(Integer id);

    List<Pet> selectByStatus(String status);

    List<Pet> selectAll();

    int updateByPrimaryKey(Pet record);
}