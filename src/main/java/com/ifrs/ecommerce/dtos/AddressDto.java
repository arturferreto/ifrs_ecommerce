package com.ifrs.ecommerce.dtos;

import jakarta.validation.constraints.NotBlank;

public record AddressDto(
        @NotBlank(message = "A rua não foi informado.") String street,
        @NotBlank(message = "A cidade não foi informada.") String city,
        @NotBlank(message = "O estado não foi informado.") String state,
        @NotBlank(message = "O CEP não foi informado.") String country,
        @NotBlank(message = "O CEP não foi informado.") String zipCode,
        String complement
) {
}
