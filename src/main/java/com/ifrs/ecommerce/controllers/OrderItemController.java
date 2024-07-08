package com.ifrs.ecommerce.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ifrs.ecommerce.models.OrderItem;
import com.ifrs.ecommerce.responses.DefaultResponse;
import com.ifrs.ecommerce.services.OrderItemService;

import java.util.List;

@RequestMapping("orders/{orderId}/items")
@RestController
public class OrderItemController {
    private final OrderItemService orderItemService;

    public OrderItemController (OrderItemService orderItemService){
        this.orderItemService = orderItemService;
    }

    @GetMapping
    public ResponseEntity<DefaultResponse> all(@PathVariable Integer orderId) {
        List<OrderItem> items = orderItemService.all(orderId);

        return DefaultResponse.build(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DefaultResponse> one(@PathVariable Integer id) {
        OrderItem item = orderItemService.one(id);

        if (item == null) {
            return DefaultResponse.build("Item not found", HttpStatus.NOT_FOUND);
        }

        return DefaultResponse.build(item);
    }

}
