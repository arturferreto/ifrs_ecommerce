package com.ifrs.ecommerce.dtos;

import jakarta.validation.constraints.NotNull;

public record CartDto(
        @NotNull(message = "O id do desconto não foi informado.") Integer discountId
) {}
