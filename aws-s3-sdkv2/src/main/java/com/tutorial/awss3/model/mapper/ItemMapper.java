package com.tutorial.awss3.model.mapper;

import com.tutorial.awss3.model.dto.ItemDto;
import com.tutorial.awss3.model.entity.Item;
import com.tutorial.awss3.model.request.ItemRequest;

public interface ItemMapper {

    ItemDto toDto(ItemRequest itemRequest);
    ItemDto toDto(Item item);
}
