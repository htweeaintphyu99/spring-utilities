package com.tutorial.springaudit.service;

import com.tutorial.springaudit.entity.Item;
import com.tutorial.springaudit.entity.dto.ItemDto;
import com.tutorial.springaudit.mapper.ItemMapper;
import com.tutorial.springaudit.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{

    private final ItemRepository itemRepository;

    private final ItemMapper itemMapper;

    @Override
    public ItemDto createItem(ItemDto itemDto) {

        Item item = itemMapper.toEntity(itemDto);
        Item savedItem = itemRepository.save(item);
        return itemMapper.toDto(savedItem);
    }

    @Override
    public ItemDto updateItem(Long id, ItemDto itemDto) {

        Item item = itemRepository.findById(id).orElseThrow(() -> new RuntimeException("Item not found!"));
        item.setName(itemDto.getName());
        item.setPrice(itemDto.getPrice());
        item.setSku(itemDto.getSku());

        Item savedItem = itemRepository.save(item);
        return itemMapper.toDto(savedItem);
    }

    @Override
    public List<ItemDto> getAllItems() {

        List<Item> items = itemRepository.findAll();
        return items.stream().map(itemMapper :: toDto).collect(Collectors.toList());
    }

    @Override
    public void deleteItem(Long id) {

        Item item = itemRepository.findById(id).orElseThrow(() -> new RuntimeException("Item not found!"));
        itemRepository.delete(item);
    }
}
