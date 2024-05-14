package com.tutorial.querydsl.service;

import com.tutorial.querydsl.model.dto.ItemDto;

import java.util.List;

public interface ItemService {

    ItemDto createItem(ItemDto itemDto);

    ItemDto updateItem(Long id, ItemDto itemDto);

    List<ItemDto> getAllItems();

    void deleteItem(Long id);

    List<ItemDto> findItemByName(String name);

    List<ItemDto> findItemBySkuOrName(String sku, String name);

    List<ItemDto> findItemBySkuAndName(String sku, String name);
}
