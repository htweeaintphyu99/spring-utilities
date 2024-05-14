package com.tutorial.criteria.mapper;

import com.tutorial.criteria.entity.Item;
import com.tutorial.criteria.entity.dto.ItemDto;
import com.tutorial.criteria.entity.request.ItemRequest;
import com.tutorial.criteria.entity.response.ItemResponse;

public interface ItemMapper {

    ItemDto toDto(Item item);

    ItemResponse toResponse(ItemDto itemDto);

    ItemDto toDto(ItemRequest itemRequest);

    Item toEntity(ItemDto itemDto);
}
