package com.example.multitenantusers.controller;

import com.example.multitenantusers.model.Item;
import com.example.multitenantusers.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ItemController {

    private final ItemRepository itemRepository;

    @PostMapping("/items")
    public ResponseEntity<?> createItem(@RequestBody Item item) {
        Item savedItem = itemRepository.save(item);
        return new ResponseEntity<>(savedItem, HttpStatus.OK);
    }
}
