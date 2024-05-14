package com.tutorial.querydsl.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
public class ItemResponse {

    private Long id;
    private String name;
    private Long sku;
    private BigDecimal price;
    private CategoryResponse categoryResponse;
}
