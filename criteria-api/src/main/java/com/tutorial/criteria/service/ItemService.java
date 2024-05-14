package com.tutorial.criteria.service;

import com.tutorial.criteria.entity.dto.ItemDto;

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
