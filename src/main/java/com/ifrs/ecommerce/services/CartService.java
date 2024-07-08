package com.ifrs.ecommerce.services;

import com.ifrs.ecommerce.dtos.CartCheckoutDto;
import com.ifrs.ecommerce.dtos.CartDto;
import com.ifrs.ecommerce.enums.FulfillmentStatusEnum;
import com.ifrs.ecommerce.enums.PaymentStatusEnum;
import com.ifrs.ecommerce.models.*;
import com.ifrs.ecommerce.repositories.CartItemRepository;
import com.ifrs.ecommerce.repositories.CartRepository;
import com.ifrs.ecommerce.repositories.OrderItemRepository;
import com.ifrs.ecommerce.repositories.OrderRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final AddressService addressService;
    private final DiscountService discountService;
    private final ProductFeatureService productFeatureService;

    public CartService(
            CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            AddressService addressService,
            DiscountService discountService,
            ProductFeatureService productFeatureService
    ) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.addressService = addressService;
        this.discountService = discountService;
        this.productFeatureService = productFeatureService;
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

    @Transactional
    public Object checkout(CartCheckoutDto dto) {
        Cart cart = cartRepository.findByUser(getUser()).orElse(null);

        if (cart == null) {
            return "cart not found";
        }

        if (cartItemRepository.countByCart(cart) == 0) {
            return "cart is empty";
        }

        Address address = addressService.one(dto.addressId());

        if (address == null) {
            return "address not found";
        }

        // create the order
        Order order = new Order();
        order.setUser(getUser());
        order.setAddress(address);

        if (cart.getDiscount() != null) {
            order.setDiscount(cart.getDiscount());
        }

        order.setPaymentStatus(PaymentStatusEnum.PENDING);
        order.setFulfillmentStatus(FulfillmentStatusEnum.PENDING);
        order.setTotalAmount(cart.getTotalAmount());
        orderRepository.save(order);

        // move cart items to order items
        Iterable<Object> cartItems = cartItemRepository.findAllByCart(cart);

        for (Object cartItem : cartItems) {
            // verify if the product feature is in stock
            if (((CartItem) cartItem).getProductFeature().getQuantity() < ((CartItem) cartItem).getQuantity()) {
                return "product feature out of stock";
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProductFeature(((CartItem) cartItem).getProductFeature());
            orderItem.setQuantity(((CartItem) cartItem).getQuantity());
            orderItemRepository.save(orderItem);

            // update product feature stock
            productFeatureService.updateStock(((CartItem) cartItem).getProductFeature(), ((CartItem) cartItem).getQuantity());
        }

        // update discount usage
        if (cart.getDiscount() != null) {
            discountService.updateUsage(cart.getDiscount());
        }

        // clear cart
        cartItemRepository.deleteAllByCart(cart);
        cartRepository.delete(cart);

        return order;
    }
}
