package com.tutorial.springaudit.entity.request;

import javax.validation.constraints.*;
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
