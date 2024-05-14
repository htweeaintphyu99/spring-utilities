package com.tutorial.awss3.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutorial.awss3.model.dto.ItemDto;
import com.tutorial.awss3.model.mapper.ItemMapper;
import com.tutorial.awss3.service.impl.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ItemController {

    private final ItemService itemService;

    private final ItemMapper itemMapper;

    @GetMapping("/list")
    public ResponseEntity<List<String>> listObjects() {
        return new ResponseEntity<>(itemService.listObjects(), HttpStatus.OK);
    }

    @PostMapping("/create-folder/{name}")
    public ResponseEntity<String> createFolder(@PathVariable String name) {
        return new ResponseEntity<>(itemService.createFolder(name), HttpStatus.OK);
    }

    @PostMapping("/items")
    public ItemDto createItem(@RequestPart("body") String itemObj, @RequestPart("files") List<MultipartFile> files) throws IOException {

        ItemDto itemDto = convertToItemDto(itemObj);
        return itemService.createItem(itemDto, files);
    }

    @PutMapping("/items/{id}")
    public ItemDto updateItem(@PathVariable Long id, @RequestPart("body") String itemObj, @RequestPart("files") List<MultipartFile> files) throws IOException {

        ItemDto itemDto = convertToItemDto(itemObj);
        return itemService.updateItem(id, itemDto, files);
    }

    @GetMapping("/download")
    public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam String fileName) {
        byte[] data = itemService.downloadFile(fileName);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity.ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFile(@RequestParam String fileName) {
        return new ResponseEntity<>(itemService.deleteFile(fileName), HttpStatus.OK);
    }

    @DeleteMapping("/deleteAll/{dirName}")
    public ResponseEntity<List<String>> deleteAllInDir(@PathVariable String dirName) {
        return new ResponseEntity<>(itemService.deleteFolder(dirName), HttpStatus.OK);
    }

    private ItemDto convertToItemDto(String itemObj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ItemDto itemDto = objectMapper.readValue(itemObj, ItemDto.class);
        return itemDto;
    }
}
