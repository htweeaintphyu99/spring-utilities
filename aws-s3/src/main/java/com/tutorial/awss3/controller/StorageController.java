package com.tutorial.awss3.controller;

import com.tutorial.awss3.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class StorageController {

    @Autowired
    private StorageService storageService;

    @GetMapping("/list")
    public ResponseEntity<List<String>> listObjects() {
        return new ResponseEntity<>(storageService.listObjects(), HttpStatus.OK);
    }

    @PostMapping("/create-folder/{name}")
    public ResponseEntity<String> createFolder(@PathVariable String name) {
        return new ResponseEntity<>(storageService.createFolder(name), HttpStatus.OK);
    }

    @PostMapping("/upload/{dirName}")
    public ResponseEntity<String> uploadFileToDirectory(@PathVariable String dirName, @RequestParam(value = "file")MultipartFile file) {
        return new ResponseEntity<>(storageService.uploadFile(dirName, file), HttpStatus.OK);
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) {
        byte[] data = storageService.downloadFile(fileName);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity.ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFile(@RequestParam String fileName) {
        return new ResponseEntity<>(storageService.deleteFile(fileName), HttpStatus.OK);
    }

    @DeleteMapping("/deleteAll/{dirName}")
    public ResponseEntity<List<String>> deleteAllInDir(@PathVariable String dirName) {
        return new ResponseEntity<>(storageService.deleteFolder(dirName), HttpStatus.OK);
    }
}
