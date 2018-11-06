package com.lzx.web;

import com.lzx.dao.UserMapper;
import com.lzx.entity.User;
import com.lzx.vo.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @PostMapping
    @ResponseBody
    public ResponseEntity addUser(User user) {
        int num  = userMapper.insert(user);
        return ResponseEntity.status(200).body(user);
    }

    @PostMapping("/createWithArray")
    @ResponseBody
    public ResponseEntity addUser(ArrayList<User> list) {
        for (User user : list) {
            userMapper.insert(user);
        }
        return ResponseEntity.status(200).body(list);
    }

    @PostMapping("/createWithList")
    @ResponseBody
    public ResponseEntity addUser(List<User> list) {
        for (User user : list) {
            userMapper.insert(user);
        }
        return ResponseEntity.status(200).body(list);
    }

    @GetMapping("/login")
    @ResponseBody
    public ResponseEntity login(User user) {
        User u = userMapper.selectByNameAndPassword(user);
        if (u != null) {
            userMapper.updateStatus(user);
            return ResponseEntity.status(200).body(u);
        } else {
            return ResponseEntity.status(200).body(new ApiResponse(400, "error", "Invalid username/password supplied"));
        }
    }

    @GetMapping("/logout")
    @ResponseBody
    public ResponseEntity logout(@SessionAttribute User user) {
        int num = userMapper.updateStatus(user);
        return ResponseEntity.status(200).body(user);
    }

    @GetMapping("/{username}")
    @ResponseBody
    public ResponseEntity findByUserName(@PathVariable String userName) {
        if (userName == "") {
            return ResponseEntity.status(400).body(new ApiResponse(400, "error", "Invalid username supplied"));
        } else {
            User user = userMapper.selectByUserName(userName);
            if (user != null) {
                return ResponseEntity.status(200).body(user);
            } else {
                return ResponseEntity.status(400).body(new ApiResponse(400, "error", "User not found"));
          }
        }
    }

    @PutMapping("/{username}")
    @ResponseBody
    public ResponseEntity updateUser(@PathVariable String userName) {
        User users = null;
        if (userName == "") {
            return ResponseEntity.status(400).body(new ApiResponse(400, "error", "Invalid username supplied"));
        } else {
            User user = userMapper.selectByUserName(userName);
            if (user != null) {
                userMapper.updateByPrimaryKey(user);
                return ResponseEntity.status(200).body(user);
            } else {
                return ResponseEntity.status(400).body(new ApiResponse(400, "error", "User not found"));
            }
        }
    }

    @DeleteMapping("/{username}")
    @ResponseBody
    public ResponseEntity deleteUser(@PathVariable String userName) {
        if (userName == "") {
            return ResponseEntity.status(400).body(new ApiResponse(400, "error", "Invalid username supplied"));
        } else {
            User user = userMapper.selectByUserName(userName);
            if (user != null) {
                userMapper.deleteByUserName(userName);
            } else {

                return ResponseEntity.status(400).body(new ApiResponse(400, "error", "User not found"));
            }
        }
        return ResponseEntity.status(200).body(new ApiResponse(1,"","successful operation"));
    }
}

