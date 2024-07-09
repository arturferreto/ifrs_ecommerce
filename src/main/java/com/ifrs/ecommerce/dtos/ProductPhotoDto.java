package com.ifrs.ecommerce.dtos;

import jakarta.validation.constraints.NotBlank;

public record ProductPhotoDto(
    @NotBlank(message = "A URL não foi informada.") String photoUrl
) {
}
