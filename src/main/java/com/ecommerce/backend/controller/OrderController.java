package com.ecommerce.backend.controller;

import com.ecommerce.backend.model.Order;
import com.ecommerce.backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 🧾 Place order
    @PostMapping("/{userId}")
public Order placeOrder(@PathVariable Long userId,
                        @RequestHeader("Authorization") String token) {

    if (token == null || !token.startsWith("Bearer ")) {
        throw new RuntimeException("Unauthorized");
    }

    return orderService.placeOrder(userId);
}

    // 📦 Get orders
    @GetMapping("/{userId}")
    public List<Order> getOrders(@PathVariable Long userId) {
        return orderService.getOrders(userId);
    }
}