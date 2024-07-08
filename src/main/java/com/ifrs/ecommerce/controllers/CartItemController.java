package com.ifrs.ecommerce.controllers;

import com.ifrs.ecommerce.dtos.CartItemDto;
import com.ifrs.ecommerce.models.CartItem;
import com.ifrs.ecommerce.responses.DefaultResponse;
import com.ifrs.ecommerce.services.CartItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/cart-items")
@RestController
public class CartItemController {
    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @GetMapping
    public ResponseEntity<DefaultResponse> all() {
        List<CartItem> cartItems = cartItemService.all();

        return DefaultResponse.build(cartItems);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DefaultResponse> one(@PathVariable Integer id) {
        CartItem cartItem = cartItemService.one(id);

        if (cartItem == null) {
            return DefaultResponse.build("Cart item not found", HttpStatus.NOT_FOUND);
        }

        return DefaultResponse.build(cartItem);
    }

    @PostMapping
    public ResponseEntity<DefaultResponse> store(@RequestBody @Valid CartItemDto cartItemDto) {
        Object result = cartItemService.store(cartItemDto);

        if (result instanceof String message) {
            return DefaultResponse.build(message, HttpStatus.BAD_REQUEST);
        }

        return DefaultResponse.build(result, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DefaultResponse> update(@PathVariable Integer id, @RequestBody @Valid CartItemDto cartItemDto) {
        Object result = cartItemService.update(id, cartItemDto);

        if (result instanceof String message) {
            return DefaultResponse.build(message, HttpStatus.BAD_REQUEST);
        }

        return DefaultResponse.build(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DefaultResponse> delete(@PathVariable Integer id) {
        cartItemService.delete(id);

        return DefaultResponse.build("Cart item deleted");
    }
}
