package com.tutorial.criteria.controller;

import com.tutorial.criteria.entity.dto.ItemDto;
import com.tutorial.criteria.entity.request.ItemRequest;
import com.tutorial.criteria.entity.response.ItemResponse;
import com.tutorial.criteria.mapper.ItemMapper;
import com.tutorial.criteria.repository.ItemRepository;
import com.tutorial.criteria.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
