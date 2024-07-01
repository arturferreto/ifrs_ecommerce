package com.ifrs.ecommerce.dtos;

public record ProductUpdateDto(
    String name,
    Double price,
    String description
) {}
