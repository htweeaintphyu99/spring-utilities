package com.tutorial.springaudit.mapper;

import com.tutorial.springaudit.entity.Item;
import com.tutorial.springaudit.entity.dto.ItemDto;
import com.tutorial.springaudit.entity.request.ItemRequest;
import com.tutorial.springaudit.entity.response.ItemResponse;

public interface ItemMapper {

    ItemDto toDto(Item item);

    ItemResponse toResponse(ItemDto itemDto);

    ItemDto toDto(ItemRequest itemRequest);

    Item toEntity(ItemDto itemDto);
}
