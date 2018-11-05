package com.lzx.web;

import com.lzx.dao.CategoryMapper;
import com.lzx.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryMapper category;

    @GetMapping
    @ResponseBody
    public List<Category>  selectAll(Model model){
        List<Category> categories = category.selectAll();
        return categories;
    }
}
