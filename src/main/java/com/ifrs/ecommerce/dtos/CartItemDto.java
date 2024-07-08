package com.ifrs.ecommerce.dtos;

import jakarta.validation.constraints.NotNull;

public record CartItemDto(
        @NotNull(message = "O produto não foi informado") Integer productFeatureId,
        @NotNull(message = "A quantidade não foi informada") Integer quantity
) {}
