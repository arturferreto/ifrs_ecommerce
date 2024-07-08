package com.ifrs.ecommerce.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ifrs.ecommerce.dtos.DiscountDto;
import com.ifrs.ecommerce.models.Discount;
import com.ifrs.ecommerce.responses.DefaultResponse;
import com.ifrs.ecommerce.services.DiscountService;

import jakarta.validation.Valid;

@RequestMapping("/discounts")
@RestController
public class DiscountController {
    private final DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @GetMapping
    public ResponseEntity<DefaultResponse> all() {
        List<Discount> discounts = discountService.all();

        return DefaultResponse.build(discounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DefaultResponse> one(@PathVariable Integer id) {
        Discount discount = discountService.one(id);

        if (discount == null) {
            return  DefaultResponse.build("Discount not found", HttpStatus.NOT_FOUND);
        }

        return DefaultResponse.build(discount);
    }

    @PostMapping
    public ResponseEntity<DefaultResponse> store(@RequestBody @Valid DiscountDto discountDto) {
        Discount discount = discountService.store(discountDto);

        return DefaultResponse.build(discount, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DefaultResponse> update(@PathVariable Integer id, @RequestBody @Valid DiscountDto discountDto) {
        Discount discount = discountService.update(id, discountDto);

        if (discount == null){
            return DefaultResponse.build("Discount not fount", HttpStatus.NOT_FOUND);
        }

        return DefaultResponse.build(discount);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DefaultResponse> delete(@PathVariable Integer id) {
        boolean deleted = discountService.delete(id);

        if (!deleted) {
            return DefaultResponse.build("Discount not fount", HttpStatus.NOT_FOUND);
        }

        return DefaultResponse.build("Product deleted");
    }
}
