package com.ifrs.ecommerce.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ifrs.ecommerce.models.Order;
import com.ifrs.ecommerce.models.User;
import com.ifrs.ecommerce.repositories.OrderRepository;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService (
        OrderRepository orderRepository
    ) {
        this.orderRepository = orderRepository;
    }

    private User getUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public List<Order> all() {
        List<Order> orders = new ArrayList();

        orderRepository.findAllByUser(getUser()).forEach(orders::add);

        return orders;
    }

    public Order one(Integer id) {
        return orderRepository.findById(id).orElse(null);
    }

}
