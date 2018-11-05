package com.lzx.web;

import com.lzx.dao.OrderMapper;
import com.lzx.entity.Order;
import com.lzx.vo.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private OrderMapper orderMapper;

    private Order order;

    @GetMapping("/inventory")
    @ResponseBody
    public ResponseEntity getInventory() {
        order = orderMapper.selectByStatus("inventoru");
        if (order != null) {
            return ResponseEntity.status(200).body(order);
        }
        return null;
    }

    @PostMapping("/order")
    @ResponseBody
    public ResponseEntity addOrder(Order order) {
        order = orderMapper.insert(order);
        if (order != null) {
            return ResponseEntity.status(200).body(order);
        } else {
            return ResponseEntity.status(400).body(new ApiResponse(1, "error", "Invalid Order"));
        }
    }

    @GetMapping("/order/{orderId}")
    @ResponseBody
    public ResponseEntity getById(@PathVariable int orderId) {
        if (orderId == 0) {
            return ResponseEntity.status(400).body(new ApiResponse(1, "error", "Invalid ID supplied"));
        } else {
            order = orderMapper.selectByPrimaryKey(orderId);
            if (order != null) {
                return ResponseEntity.status(200).body(order);
            } else {
                return ResponseEntity.status(404).body(new ApiResponse(2, "error", "Order not found"));
            }
        }
    }

    @DeleteMapping("/order/{orderId}")
    @ResponseBody
    public ResponseEntity deleteById(@PathVariable int orderId) {
        if (orderId == 0) {
            return ResponseEntity.status(400).body(new ApiResponse(1, "error", "Invalid ID supplied"));
        } else {
            if (orderMapper.deleteByPrimaryKey(orderId) != 0) {
                return ResponseEntity.status(200).body(new ApiResponse(200, "", "successful operation"));
            } else {
                return ResponseEntity.status(404).body(new ApiResponse(2, "error", "Order not found"));
            }
        }
    }
}
