package com.ifrs.ecommerce.dtos;

import jakarta.validation.constraints.NotBlank;

public record LoginUserDto(
        @NotBlank(message = "O email não foi informado.") String email,
        @NotBlank(message = "A senha não foi informado.") String password
) {}
