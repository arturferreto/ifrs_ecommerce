package com.ifrs.ecommerce.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ifrs.ecommerce.models.Order;
import com.ifrs.ecommerce.responses.DefaultResponse;
import com.ifrs.ecommerce.services.OrderService;


@RequestMapping("/orders")
@RestController
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DefaultResponse> one(@PathVariable Integer id) {
        Order order = orderService.one(id);

        if (order == null) {
            return DefaultResponse.build("Order not found.", HttpStatus.NOT_FOUND);
        }

        return DefaultResponse.build(order);
    }

    @GetMapping
    public ResponseEntity<DefaultResponse> all() {
        List<Order> orders = orderService.all();

        return DefaultResponse.build(orders);
    }
}
