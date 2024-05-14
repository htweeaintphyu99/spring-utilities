package com.tutorial.criteria.service;

import com.tutorial.criteria.entity.Item;
import com.tutorial.criteria.entity.dto.ItemDto;
import com.tutorial.criteria.mapper.ItemMapper;
import com.tutorial.criteria.repository.ItemRepository;
import com.tutorial.criteria.service.specification.ItemSpecification;
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

    @Override
    public List<ItemDto> findItemByName(String name) {
        List<Item> items = itemRepository.findAll(ItemSpecification.getItemByName(name));
        return items.stream().map(itemMapper :: toDto).collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> findItemBySkuOrName(String sku, String name) {
        List<Item> items =  itemRepository.findAll(ItemSpecification.getItemBySkuOrName(sku, name));
        return items.stream().map(itemMapper :: toDto).collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> findItemBySkuAndName(String sku, String name) {
        List<Item> items =  itemRepository.findAll(ItemSpecification.getItemBySkuAndName(sku, name));
        return items.stream().map(itemMapper :: toDto).collect(Collectors.toList());
    }
}
