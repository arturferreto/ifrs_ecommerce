package com.ifrs.ecommerce.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ifrs.ecommerce.dtos.DiscountDto;
import com.ifrs.ecommerce.models.Discount;
import com.ifrs.ecommerce.repositories.DiscountRepository;

@Service
public class DiscountService {
    private final DiscountRepository discountRepository;

    public DiscountService(DiscountRepository discountRepository){
        this.discountRepository = discountRepository;
    }

    public List<Discount> all() {
        List<Discount> discounts = new ArrayList<>();

        discountRepository.findAll().forEach(discounts::add);

        return discounts;
    }

    public Discount one(Integer id) {
        return discountRepository.findById(id).orElse(null);
    }

    public Discount store(DiscountDto dto) {
        Discount discount = new Discount();
        discount.setCode(dto.code());
        discount.setPercentage(dto.percentage());
        discount.setQuantity(dto.quantity());
        discountRepository.save(discount);
        return discount;
    }

    public Discount update(Integer id, DiscountDto dto){
         Discount discount = discountRepository.findById(id).orElse(null);

         if (discount == null) {
            return null;
         }

         discount.setCode(dto.code());
         discount.setPercentage(dto.percentage());
         discount.setQuantity(dto.quantity());
         discountRepository.save(discount);
         return discount;
    }

    public boolean delete(Integer id) {
        Discount discount = discountRepository.findById(id).orElse(null);

        if (discount == null){
            return false;
        }

        discountRepository.delete(discount);

        return true;
    }

}
