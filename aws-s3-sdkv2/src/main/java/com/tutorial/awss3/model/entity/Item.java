package com.tutorial.awss3.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;


@Entity
@Table
@Getter @Setter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sku;

    private String name;

    private BigDecimal price;


    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<Image> images;
}
