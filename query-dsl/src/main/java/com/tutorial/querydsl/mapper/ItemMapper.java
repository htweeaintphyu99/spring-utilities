package com.tutorial.querydsl.mapper;

import com.tutorial.querydsl.model.entity.Item;
import com.tutorial.querydsl.model.dto.ItemDto;
import com.tutorial.querydsl.model.request.ItemRequest;
import com.tutorial.querydsl.model.response.ItemResponse;

public interface ItemMapper {

    ItemDto toDto(Item item);

    ItemResponse toResponse(ItemDto itemDto);

    ItemDto toDto(ItemRequest itemRequest);

    Item toEntity(ItemDto itemDto);
}
