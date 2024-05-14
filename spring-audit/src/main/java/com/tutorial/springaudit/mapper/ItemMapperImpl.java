package com.tutorial.springaudit.mapper;

import com.tutorial.springaudit.entity.Item;
import com.tutorial.springaudit.entity.dto.ItemDto;
import com.tutorial.springaudit.entity.request.ItemRequest;
import com.tutorial.springaudit.entity.response.ItemResponse;
import lombok.Builder;
import org.springframework.stereotype.Component;

@Component
public class ItemMapperImpl implements ItemMapper{

    @Override
    public ItemDto toDto(Item item) {
        ItemDto itemDto = ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .sku(item.getSku())
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
                .build();
        return itemResponse;
    }

    @Override
    public ItemDto toDto(ItemRequest itemRequest) {
        ItemDto itemDto = ItemDto.builder()
                .name(itemRequest.getName())
                .price(itemRequest.getPrice())
                .sku(itemRequest.getSku())
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
