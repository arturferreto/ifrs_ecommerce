package com.ifrs.ecommerce.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


public record RegisterUserDto(
        @NotBlank(message = "O email não foi informado.") @Email(message = "O email informado não é válido.") String email,
        @NotBlank(message = "A senha não foi informado.") String password,
        @NotBlank(message = "O nome não foi informado.") String name
) {}
