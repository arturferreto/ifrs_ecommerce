package com.ifrs.ecommerce.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductPhotoDto(
    @NotNull(message = "O id do produto não foi encontrado.") Integer productId,
    @NotBlank(message = "A URL não foi informada.") String photoUrl,
    boolean isFavorite
) {
}
