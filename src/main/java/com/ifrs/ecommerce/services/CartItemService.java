package com.ifrs.ecommerce.services;

import com.ifrs.ecommerce.dtos.CartItemDto;
import com.ifrs.ecommerce.models.Cart;
import com.ifrs.ecommerce.models.CartItem;
import com.ifrs.ecommerce.models.Discount;
import com.ifrs.ecommerce.models.ProductFeature;
import com.ifrs.ecommerce.repositories.CartItemRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartService cartService;
    private final ProductFeatureService productFeatureService;

    public CartItemService(
            CartItemRepository cartItemRepository,
            CartService cartService,
            ProductFeatureService productFeatureService
    ) {
        this.cartItemRepository = cartItemRepository;
        this.cartService = cartService;
        this.productFeatureService = productFeatureService;
    }

    private Cart getCart() {
        return cartService.one();
    }

    public List<CartItem> all() {
        Cart cart = getCart();

        List<CartItem> cartItems = new ArrayList<>();
        cartItemRepository.findAllByCart(cart).forEach(cartItem -> cartItems.add((CartItem) cartItem));

        return cartItems;
    }

    public CartItem one(Integer id) {
        Cart cart = getCart();

        return cartItemRepository.findById(id).orElse(null);
    }

    public Object store(CartItemDto dto) {
        Cart cart = getCart();

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);

        ProductFeature productFeature = productFeatureService.one(dto.productFeatureId());

        if (productFeature == null) {
            return "product feature not found";
        }

        // verify if the product feature is in stock
        if (productFeature.getQuantity() < dto.quantity()) {
            return "product feature out of stock";
        }

        // verify if the product feature has been stored in the cart
        List<CartItem> cartItems = all();
        for (CartItem item : cartItems) {
            if (item.getProductFeature().getId() == productFeature.getId()) {
                return "product feature already in cart";
            }
        }

        cartItem.setProductFeature(productFeature);

        cartItem.setQuantity(dto.quantity());
        cartItemRepository.save(cartItem);

        totalizeCart();

        return cartItem;
    }

    public Object update(Integer id, CartItemDto dto) {
        Cart cart = getCart();

        CartItem cartItem = cartItemRepository.findById(id).orElse(null);

        if (cartItem == null) {
            return "cart item not found";
        }

        ProductFeature productFeature = productFeatureService.one(dto.productFeatureId());

        if (productFeature == null) {
            return "product feature not found";
        }

        // verify if the product feature is in stock
        if (productFeature.getQuantity() < dto.quantity()) {
            return "product feature out of stock";
        }

        // verify if the product feature has been stored in the cart
        List<CartItem> cartItems = all();
        for (CartItem item : cartItems) {
            if (item.getProductFeature().getId() == productFeature.getId()) {
                return "product feature already in cart";
            }
        }

        cartItem.setProductFeature(productFeature);

        cartItem.setQuantity(dto.quantity());
        cartItemRepository.save(cartItem);

        totalizeCart();

        return cartItem;
    }

    public void delete(Integer id) {
        Cart cart = getCart();

        cartItemRepository.findById(id).ifPresent(cartItemRepository::delete);

        totalizeCart();
    }

    private void totalizeCart() {
        Cart cart = getCart();

        List<CartItem> cartItems = all();

        double total = 0.0;
        for (CartItem cartItem : cartItems) {
            total += cartItem.getProductFeature().getProduct().getPrice() * cartItem.getQuantity();
        }

        if (cart.getDiscount() != null) {
            Discount discount = cart.getDiscount();
            total -= total * discount.getPercentage() / 100;
        }

        cart.setTotalAmount(total);
        cartService.updateTotalAmount(cart);
    }
}
