package com.tutorial.criteria.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


import java.math.BigDecimal;

@Getter @Setter
@ToString
@NoArgsConstructor
@Entity
@Table

public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long sku;
    private BigDecimal price;

}
