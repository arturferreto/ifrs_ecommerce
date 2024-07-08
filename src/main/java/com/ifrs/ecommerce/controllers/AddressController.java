package com.ifrs.ecommerce.controllers;

import com.ifrs.ecommerce.dtos.AddressDto;
import com.ifrs.ecommerce.models.Address;
import com.ifrs.ecommerce.responses.DefaultResponse;
import com.ifrs.ecommerce.services.AddressService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/profile/addresses")
@RestController
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public ResponseEntity<DefaultResponse> all() {
        List<Address> address = addressService.all();

        return DefaultResponse.build(address);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DefaultResponse> one(@PathVariable Integer id) {
        Address address = addressService.one(id);

        if (address == null) {
            return DefaultResponse.build("Address not found", HttpStatus.NOT_FOUND);
        }

        return DefaultResponse.build(address);
    }

    @PostMapping
    public ResponseEntity<DefaultResponse> store(@RequestBody @Valid AddressDto addressDto) {
        Address address = addressService.store(addressDto);

        return DefaultResponse.build(address, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DefaultResponse> update(@PathVariable Integer id, @RequestBody @Valid AddressDto addressDto) {
        Address address = addressService.update(id, addressDto);

        if (address == null) {
            return DefaultResponse.build("Address not found", HttpStatus.NOT_FOUND);
        }

        return DefaultResponse.build(address);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DefaultResponse> delete(@PathVariable Integer id) {
        boolean deleted = addressService.delete(id);

        if (!deleted) {
            return DefaultResponse.build("Address not found", HttpStatus.NOT_FOUND);
        }

        return DefaultResponse.build("Address deleted");
    }
}
