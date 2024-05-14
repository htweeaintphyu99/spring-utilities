package com.tutorial.querydsl.mapper;

import com.tutorial.querydsl.model.entity.Item;
import com.tutorial.querydsl.model.dto.ItemDto;
import com.tutorial.querydsl.model.request.ItemRequest;
import com.tutorial.querydsl.model.response.ItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ItemMapperImpl implements ItemMapper{

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ItemDto toDto(Item item) {
        ItemDto itemDto = ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .sku(item.getSku())
                .categoryDto(categoryMapper.toDto(item.getCategory()))
                .build();
        return itemDto;
    }

    @Override
    public ItemResponse toResponse(ItemDto itemDto) {
        ItemResponse itemResponse = ItemResponse.builder()
                .id(itemDto.getId())
                .name(itemDto.getName())
                .price(itemDto.getPrice())
                .sku(itemDto.getSku())
                .categoryResponse(categoryMapper.toResponse(itemDto.getCategoryDto()))
                .build();
        return itemResponse;
    }

    @Override
    public ItemDto toDto(ItemRequest itemRequest) {
        ItemDto itemDto = ItemDto.builder()
                .name(itemRequest.getName())
                .price(itemRequest.getPrice())
                .sku(itemRequest.getSku())
                .categoryDto(categoryMapper.toDto(itemRequest.getCategoryRequest()))
                .build();
        return itemDto;
    }

    @Override
    public Item toEntity(ItemDto itemDto) {
        Item item = new Item();
        item.setName(itemDto.getName());
        item.setSku(itemDto.getSku());
        item.setPrice(itemDto.getPrice());
        return item;
    }
}
