package com.ifrs.ecommerce.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductStoreDto(
    @NotBlank(message = "O nome não foi informado.") String name,
    @NotNull(message = "O preço não foi informado.") Double price,
    @NotBlank(message = "A descrição não foi informado.") String description
) {}