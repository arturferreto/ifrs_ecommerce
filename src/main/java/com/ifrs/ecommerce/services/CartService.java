package com.ifrs.ecommerce.services;

import com.ifrs.ecommerce.dtos.CartDto;
import com.ifrs.ecommerce.models.Cart;
import com.ifrs.ecommerce.models.Discount;
import com.ifrs.ecommerce.models.User;
import com.ifrs.ecommerce.repositories.CartRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final DiscountService discountService;

    public CartService(CartRepository cartRepository, DiscountService discountService) {
        this.cartRepository = cartRepository;
        this.discountService = discountService;
    }

    // one, store and update
    private User getUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public Cart one() {
        return cartRepository.findByUser(getUser()).orElse(null);
    }

    public Cart store() {
        Cart cart = new Cart();
        cart.setUser(getUser());
        cart.setTotalAmount(0.0);
        cartRepository.save(cart);
        return cart;
    }

    public Object update(CartDto dto) {
        Cart cart = cartRepository.findByUser(getUser()).orElse(null);

        if (cart == null) {
            cart = store();
        }

        Discount discount = discountService.one(dto.discountId());

        if (discount == null) {
            return "discount not found";
        }

        cart.setDiscount(discount);

        cartRepository.save(cart);

        return cart;
    }

    public void updateTotalAmount(Cart cart) {
        cart.setTotalAmount(cart.getTotalAmount());
        cartRepository.save(cart);
    }
}
