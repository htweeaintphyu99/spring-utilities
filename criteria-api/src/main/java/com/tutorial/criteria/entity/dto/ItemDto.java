package com.tutorial.criteria.entity.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Builder
@Getter
@Setter
public class ItemDto {

    private Long id;
    private String name;
    private Long sku;
    private BigDecimal price;
}
