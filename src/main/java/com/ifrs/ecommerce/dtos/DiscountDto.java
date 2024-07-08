package com.ifrs.ecommerce.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DiscountDto (
    @NotBlank(message = "O código não foi informado.") String code,
    @NotNull(message = "A porcentagem não foi informada.") Integer percentage,
    @NotNull(message = "A quantidade não foi informada.") Integer quantity
) {}
