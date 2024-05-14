package com.tutorial.criteria.entity.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemRequest {

    @NotBlank
    private String name;

    @Positive
    @NotNull
    private Long sku;

    @Positive
    @NotNull
    private BigDecimal price;
}
