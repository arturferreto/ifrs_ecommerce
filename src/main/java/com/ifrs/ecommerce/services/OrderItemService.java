package com.ifrs.ecommerce.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ifrs.ecommerce.models.Order;
import com.ifrs.ecommerce.models.OrderItem;
import com.ifrs.ecommerce.repositories.OrderItemRepository;
import com.ifrs.ecommerce.repositories.OrderRepository;

@Service
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;

    public OrderItemService (
        OrderItemRepository orderItemRepository,
        OrderRepository orderRepository
    ) {
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
    }

    private Order checkOrderExists(Integer orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public OrderItem one(Integer id) {
        return orderItemRepository.findById(id).orElse(null);
    }

    public List<OrderItem> all(Integer orderId) {
        Order order = checkOrderExists(orderId);

        List<OrderItem> items = new ArrayList();
        orderItemRepository.findAllByOrder(order).forEach(item -> items.add((OrderItem) item));
        
        return items;
    }

}
