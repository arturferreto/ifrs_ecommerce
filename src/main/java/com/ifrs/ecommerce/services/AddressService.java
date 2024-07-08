package com.ifrs.ecommerce.services;

import com.ifrs.ecommerce.dtos.AddressDto;
import com.ifrs.ecommerce.dtos.ProductFeatureDto;
import com.ifrs.ecommerce.models.Address;
import com.ifrs.ecommerce.models.Product;
import com.ifrs.ecommerce.models.ProductFeature;
import com.ifrs.ecommerce.models.User;
import com.ifrs.ecommerce.repositories.AddressRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressService {
    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    private User getUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public List<Address> all() {
        List<Address> addresses = new ArrayList<>();
        addressRepository.findAllByUser(getUser()).forEach(address -> addresses.add((Address) address));

        return addresses;
    }

    public Address one(Integer id) {
        return addressRepository.findById(id).orElse(null);
    }

    public Address store(AddressDto dto) {
        Address address = new Address();

        address.setUser(getUser());
        address.setStreet(dto.street());
        address.setComplement(dto.complement());
        address.setCity(dto.city());
        address.setState(dto.state());
        address.setCountry(dto.country());
        address.setZipCode(dto.zipCode());
        addressRepository.save(address);

        return address;
    }

    public Address update(Integer id, AddressDto dto) {
        Address address = addressRepository.findById(id).orElse(null);

        if (address == null) {
            return null;
        }

        address.setUser(getUser());
        address.setComplement(dto.complement());
        address.setStreet(dto.street());
        address.setCity(dto.city());
        address.setState(dto.state());
        address.setCountry(dto.country());
        address.setZipCode(dto.zipCode());
        addressRepository.save(address);

        return address;
    }

    public boolean delete(Integer id) {
        Address address = addressRepository.findById(id).orElse(null);

        if (address == null) {
            return false;
        }

        addressRepository.delete(address);

        return true;
    }
}
