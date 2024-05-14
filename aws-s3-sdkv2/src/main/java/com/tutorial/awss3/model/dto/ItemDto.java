package com.tutorial.awss3.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter @Setter
public class ItemDto {

    private Long id;
    private String sku;
    private String name;
    private BigDecimal price;
    private List<ImageDto> images;
}
