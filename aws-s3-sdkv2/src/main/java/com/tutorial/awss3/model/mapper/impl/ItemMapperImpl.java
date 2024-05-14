package com.tutorial.awss3.model.mapper.impl;

import com.tutorial.awss3.model.dto.ItemDto;
import com.tutorial.awss3.model.entity.Item;
import com.tutorial.awss3.model.mapper.ImageMapper;
import com.tutorial.awss3.model.mapper.ItemMapper;
import com.tutorial.awss3.model.request.ItemRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class ItemMapperImpl implements ItemMapper {

    private final ImageMapper imageMapper;
    @Override
    public ItemDto toDto(ItemRequest itemRequest) {
        ItemDto itemDto = new ItemDto();
        itemDto.setName(itemRequest.getName());
        itemDto.setSku(itemRequest.getSku());
        itemDto.setPrice(itemRequest.getPrice());
        return itemDto;
    }

    @Override
    public ItemDto toDto(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getId());
        itemDto.setName(item.getName());
        itemDto.setSku(item.getSku());
        itemDto.setPrice(item.getPrice());
        itemDto.setImages(item.getImages().stream().map(imageMapper :: toDto).collect(Collectors.toList()));
        return itemDto;
    }
}
