package com.lzx.web;

import com.lzx.dao.UserMapper;
import com.lzx.entity.User;
import com.lzx.vo.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserMapper userMapper;

    @PostMapping
    @ResponseBody
    public ApiResponse addUser(User user) {
        if (userMapper.insert(user) != 0) {
            return new ApiResponse("default", "successful operation");
        }
        return new ApiResponse();
    }

    @PostMapping("/createWithArray")
    @ResponseBody
    public ApiResponse addUser(ArrayList<User> list) {
        for (User user : list) {
            userMapper.insert(user);
        }
        return new ApiResponse("default", "successful operation");
    }

    @PostMapping("/createWithList")
    @ResponseBody
    public ApiResponse addUser(List<User> list) {
        for (User user : list) {
            userMapper.insert(user);
        }
        return new ApiResponse("default", "successful operation");
    }

    @GetMapping("/login")
    @ResponseBody
    public ApiResponse login(User user) {
        if (userMapper.logIn(user) != 0) {
            userMapper.updateStatus(user);
            return new ApiResponse(200, "", "successful operation");
        } else {
            return new ApiResponse(400, "", "Invalid username/password supplied");
        }
    }

    @GetMapping("/logout")
    @ResponseBody
    public ApiResponse logout(@SessionAttribute User user) {
        userMapper.updateStatus(user);
        return new ApiResponse("default", "successful operation");
    }

    @GetMapping("/{username}")
    @ResponseBody
    public ApiResponse findByUserName(@PathVariable String userName) {
        if (userName == "") {
            return new ApiResponse(400, "", "Invalid username supplied");
        } else {
            if (userMapper.selectByUserName(userName) != null) {
                return new ApiResponse(200, "", "successful operation");
            } else {
                return new ApiResponse(404, "", "User not found");
            }
        }
    }

    @PutMapping("/{username}")
    @ResponseBody
    public ApiResponse updateUser(@PathVariable String userName) {
        if (userName == "") {
            return new ApiResponse(400, "", "Invalid username supplied");
        } else {
            User user = userMapper.selectByUserName(userName);
            if (user != null) {
                userMapper.updateByPrimaryKey(user);
            } else {
                return new ApiResponse(404, "", "User not found");
            }
        }
        return new ApiResponse();
    }

    @DeleteMapping("/{username}")
    @ResponseBody
    public ApiResponse deleteUser(@PathVariable String userName){
        if (userName == "") {
            return new ApiResponse(400, "", "Invalid username supplied");
        } else {
            User user = userMapper.selectByUserName(userName);
            if (user != null) {
                userMapper.deleteByUserName(userName);
            } else {
                return new ApiResponse(404, "", "User not found");
            }
        }
        return new ApiResponse();
    }
}
