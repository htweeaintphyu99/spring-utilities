package com.tutorial.awss3.model.request;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter@Setter
public class ItemRequest {

    private String sku;

    private String name;

    private BigDecimal price;

}
