package com.ifrs.ecommerce.dtos;

import jakarta.validation.constraints.NotNull;

public record CartCheckoutDto(
        @NotNull(message = "O id do endereço não foi encontrado.") Integer addressId
) {}
