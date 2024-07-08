package com.ifrs.ecommerce.controllers;

import com.ifrs.ecommerce.dtos.CartDto;
import com.ifrs.ecommerce.models.Cart;
import com.ifrs.ecommerce.responses.DefaultResponse;
import com.ifrs.ecommerce.services.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/cart")
@RestController
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<DefaultResponse> one() {
        Cart cart = cartService.one();

        if (cart == null) {
            cart = cartService.store();
        }

        return DefaultResponse.build(cart);
    }

    @PatchMapping
    public ResponseEntity<DefaultResponse> update(@RequestBody CartDto cartDto) {
        Object result = cartService.update(cartDto);

        if (result instanceof String message) {
            return DefaultResponse.build(message, HttpStatus.BAD_REQUEST);
        }

        return DefaultResponse.build(result);
    }
}
