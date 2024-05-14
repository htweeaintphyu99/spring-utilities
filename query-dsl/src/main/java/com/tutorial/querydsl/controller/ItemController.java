package com.tutorial.querydsl.controller;

import com.tutorial.querydsl.model.dto.ItemDto;
import com.tutorial.querydsl.model.entity.Item;
import com.tutorial.querydsl.model.request.ItemRequest;
import com.tutorial.querydsl.model.response.ItemResponse;
import com.tutorial.querydsl.mapper.ItemMapper;
import com.tutorial.querydsl.repository.ItemRepository;
import com.tutorial.querydsl.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    private final ItemMapper itemMapper;

    private final ItemRepository itemRepository;

    @GetMapping
    public List<ItemResponse> getAllItems() {
        List<ItemDto> itemDtos = itemService.getAllItems();
        return itemDtos.stream().map(itemMapper::toResponse).collect(Collectors.toList());
    }

    @PostMapping
    public ItemResponse createItem(@Valid @RequestBody ItemRequest itemRequest) {
        ItemDto itemDto = itemService.createItem(itemMapper.toDto(itemRequest));
        return itemMapper.toResponse(itemDto);
    }

    @PutMapping("/{id}")
    ItemResponse updateItem(@PathVariable Long id, @Valid @RequestBody ItemRequest itemRequest) {
        ItemDto itemDto = itemService.updateItem(id, itemMapper.toDto(itemRequest));
        return itemMapper.toResponse(itemDto);
    }

    @DeleteMapping("/{id}")
    public Long deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        return id;
    }

    @GetMapping("/findByName")
    public List<ItemDto> findItemsByName(@RequestParam String name) {
        return itemService.findItemByName(name);
    }

    @GetMapping("/findBySkuOrName")
    public List<ItemDto> findBySkuOrName(@RequestParam(required = false) String sku, @RequestParam(required = false) String name) {
        return itemService.findItemBySkuOrName(sku, name);
    }

    @GetMapping("/findBySkuAndName")
    public List<ItemDto> findBySkuAndName(@RequestParam(required = false) String sku, @RequestParam(required = false) String name) {
        return itemService.findItemBySkuAndName(sku, name);
    }
}
