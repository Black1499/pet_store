package com.lzx.web;

import com.lzx.dao.OrderMapper;
import com.lzx.entity.Order;
import com.lzx.vo.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/store")
public class StoreController {

    @Autowired
    OrderMapper orderMapper;

    @GetMapping("/inventory")
    @ResponseBody
    public ApiResponse getInventory() {
        if (orderMapper.selectByStatus("inventoru") != null) {
            return new ApiResponse(200, "", "successful operation");
        }
        return new ApiResponse();
    }

    @PostMapping("/order")
    @ResponseBody
    public ApiResponse addOrder(Order order) {
        if (orderMapper.insert(order) != 0) {
            return new ApiResponse(200, "", "successful operation");
        } else {
            return new ApiResponse(400, "", "Invalid Order");
        }
    }

    @GetMapping("/order/{orderId}")
    @ResponseBody
    public ApiResponse getById(@PathVariable int orderId) {
        if (orderId == 0) {
            return new ApiResponse(400, "", "Invalid ID supplied");
        } else {
            if (orderMapper.deleteByPrimaryKey(orderId) != 0) {
                return new ApiResponse(200, "", "successful operation");
            } else {
                return new ApiResponse(400, "", "Invalid ID supplied");
            }
        }
    }

    @DeleteMapping("/order/{orderId}")
    @ResponseBody
    public ApiResponse deleteById(@PathVariable int orderId) {
        if (orderId == 0) {
            return new ApiResponse(400, "", "Invalid ID supplied");
        } else {
            if (orderMapper.deleteByPrimaryKey(orderId) != 0) {
                return new ApiResponse(200, "", "successful operation");
            } else {
                return new ApiResponse(404, "", "Order not found");
            }
        }
    }
}
