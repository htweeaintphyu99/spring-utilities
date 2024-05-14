package com.tutorial.springaudit.service;

import com.tutorial.springaudit.entity.dto.ItemDto;

import java.util.List;

public interface ItemService {

    ItemDto createItem(ItemDto itemDto);
    ItemDto updateItem(Long id, ItemDto itemDto);
    List<ItemDto> getAllItems();
    void deleteItem(Long id);
}
