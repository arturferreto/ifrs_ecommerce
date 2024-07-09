package com.ifrs.ecommerce.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductDto(
    @NotBlank(message = "O nome não foi informado.") String name,
    @NotNull(message = "O preço não foi informado.") Double price,
    @NotBlank(message = "A descrição não foi informado.") String description,
    @NotBlank(message = "A URL da foto favorita não foi informada.") String favoritePhotoUrl
) {}
