package com.ecommerce.backend.service;

import com.ecommerce.backend.model.*;
import com.ecommerce.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartRepository cartRepository;

    // 🧠 Place Order (convert cart → order)
    public Order placeOrder(Long userId) {

        // 1. Get cart items
        List<CartItem> cartItems = cartRepository.findByUserId(userId);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // 2. Create order
        Order order = new Order();
        order.setUserId(userId);
        order.setStatus("PENDING");
        order.setCreatedAt(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);

        // 3. Save order items
        for (CartItem item : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(savedOrder.getId());
            orderItem.setProductId(item.getProductId());
            orderItem.setQuantity(item.getQuantity());

            orderItemRepository.save(orderItem);
        }

        // 4. Clear cart
        cartRepository.deleteAll(cartItems);

        return savedOrder;
    }

    // 📦 Get user orders
    public List<Order> getOrders(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}