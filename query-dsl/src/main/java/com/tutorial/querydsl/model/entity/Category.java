package com.tutorial.querydsl.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
@Table
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    private List<Item> items;

    public List<Item> saveItem(Item item) {
        if(items == null) {
            items = new ArrayList<>();
        }
        items.add(item);
        return items;
    }
}
