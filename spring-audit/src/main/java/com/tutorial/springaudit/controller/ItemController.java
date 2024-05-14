package com.tutorial.springaudit.controller;

import com.tutorial.springaudit.entity.dto.ItemDto;
import com.tutorial.springaudit.entity.request.ItemRequest;
import com.tutorial.springaudit.entity.response.ItemResponse;
import com.tutorial.springaudit.mapper.ItemMapper;
import com.tutorial.springaudit.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    private final ItemMapper itemMapper;

    @GetMapping
    public List<ItemResponse> getAllItems() {
        List<ItemDto> itemDtos = itemService.getAllItems();
        return itemDtos.stream().map(itemMapper :: toResponse).collect(Collectors.toList());
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
}
