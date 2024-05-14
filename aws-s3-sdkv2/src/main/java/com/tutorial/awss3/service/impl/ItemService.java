package com.tutorial.awss3.service.impl;

import com.tutorial.awss3.model.dto.ItemDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemService {

    List<String> listObjects();

    String createFolder(String folderName);

    byte[] downloadFile(String fileName);

    String deleteFile(String fileName);

    List<String> deleteFolder(String folderName);

    ItemDto createItem(ItemDto itemDto, List<MultipartFile> files);

    ItemDto updateItem(Long id, ItemDto itemDto, List<MultipartFile> files);
}
