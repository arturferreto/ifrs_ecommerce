package com.ifrs.ecommerce.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductFeatureDto(
    @NotNull(message = "O id do produto não foi informado.") Integer productId,
    @NotBlank(message = "O nome não foi informado.") String name,
    @NotNull(message = "A quantidade não foi informada.") Integer quantity
) {
}
