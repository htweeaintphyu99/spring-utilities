package com.tutorial.querydsl.dao;

import com.tutorial.querydsl.model.entity.Item;

import java.math.BigDecimal;
import java.util.List;

public interface ItemDao {

    List<Item> getItemsGreaterThanPrice(BigDecimal price);

    List<Item> getItemBySku(Long id);
}
