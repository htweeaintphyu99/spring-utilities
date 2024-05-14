package com.tutorial.awss3.service;

import com.tutorial.awss3.model.dto.ItemDto;
import com.tutorial.awss3.model.entity.Image;
import com.tutorial.awss3.model.entity.Item;
import com.tutorial.awss3.model.mapper.ItemMapper;
import com.tutorial.awss3.repository.ImageRepository;
import com.tutorial.awss3.repository.ItemRepository;
import com.tutorial.awss3.service.impl.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    @Value("${credentials.aws_bucket-name}")
    private String bucketName;

    private final S3Client s3Client;
    private final ItemRepository itemRepository;
    private final ImageRepository imageRepository;
    private final ItemMapper itemMapper;

    public List<String> listObjects() {
        ListObjectsRequest listObjectsRequest = ListObjectsRequest.builder()
                .bucket(bucketName)
                .build();

        List<String> objectKeys = new ArrayList<>();
        ListObjectsResponse listObjectsResponse = s3Client.listObjects(listObjectsRequest);

        for (S3Object s3Object : listObjectsResponse.contents()) {
            objectKeys.add(s3Object.key());
        }

        return objectKeys;
    }


    public String createFolder(String folderName) {
        try {
            // S3 doesn't have an explicit concept of folders, so we create an empty object with the folder path as the key
            String folderKey = folderName.endsWith("/") ? folderName : folderName + "/";

            // Create an empty file
            File emptyFile = File.createTempFile("empty", null);
            emptyFile.deleteOnExit();

            // Upload the empty file to S3
            s3Client.putObject(PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(folderKey)
                            .build(),
                    RequestBody.fromFile(emptyFile));

            return "Folder created successfully.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error creating folder.";
        }
    }

    public ItemDto createItem(ItemDto itemDto, List<MultipartFile> files) {

        Item item = new Item();
        item.setName(itemDto.getName());
        item.setSku(itemDto.getSku());
        item.setPrice(itemDto.getPrice());
        itemRepository.save(item);

        ItemDto savedItemDto = uploadImages(item, files);
        return savedItemDto;
    }

    @Override
    public ItemDto updateItem(Long id, ItemDto itemDto, List<MultipartFile> files) {

        Item item = itemRepository.findById(id).orElseThrow(() -> new RuntimeException("Item not found"));
        item.setSku(itemDto.getSku());
        item.setName(itemDto.getName());
        item.setPrice(itemDto.getPrice());

        List<Image> images = imageRepository.findByItem(item);
        for(Image image : images) {
            String fileName = image.getUrl().split("cloudfront.net/")[1];
            System.out.println(fileName);
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName).key(fileName).build();
            s3Client.deleteObject(deleteObjectRequest);
        }
        imageRepository.deleteAllInBatch(images);

        ItemDto updatedItemDto = uploadImages(item, files);
        return updatedItemDto;
    }

    private ItemDto uploadImages(Item item, List<MultipartFile> files) {
        List<Image> images = new ArrayList<>();
        for(MultipartFile file : files) {
            String fileName = System.currentTimeMillis()+"_"+file.getOriginalFilename();
            try(InputStream inputStream = file.getInputStream()) {
                PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(fileName)
                        .build();
                s3Client.putObject(putObjectRequest, (Path) file.getInputStream());
            } catch (S3Exception | IOException e) {
                e.printStackTrace();
            }

//            GetUrlRequest request = GetUrlRequest.builder().bucket(bucketName ).key(fileName).build();
//            String url = s3Client.utilities().getUrl(request).toExternalForm();
            String cloudfrontUrl = "https://d30o5oskelql40.cloudfront.net/";
            Image image = new Image();
            image.setUrl(cloudfrontUrl+fileName);
            image.setItem(item);
            images.add(image);
            imageRepository.save(image);
        }
        item.setImages(images);
        Item savedItem = itemRepository.save(item);
        return itemMapper.toDto(savedItem);
    }

    public byte[] downloadFile(String fileName) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();
        ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(getObjectRequest);
        byte[] data = objectBytes.asByteArray();
        return data;
    }

    public String deleteFile(String fileName) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                        .bucket(bucketName).key(fileName).build();
        s3Client.deleteObject(deleteObjectRequest);
        return fileName + " is removed...";
    }

    public List<String> deleteFolder(String folderName) {

        // Ensure folderName ends with "/"
        if (!folderName.endsWith("/")) {
            folderName += "/";
        }

        // List objects in the specified "folder"
        ListObjectsRequest listObjectsRequest = ListObjectsRequest.builder()
                .bucket(bucketName)
                .prefix(folderName)
                .build();

        List<String> objectKeys = new ArrayList<>();
        ListObjectsResponse listObjectsResponse = s3Client.listObjects(listObjectsRequest);

        for (S3Object s3Object : listObjectsResponse.contents()) {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName).key(s3Object.key()).build();
            s3Client.deleteObject(deleteObjectRequest);
            objectKeys.add(s3Object.key());
        }
        return objectKeys;
    }

    private File convertMultipartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch(IOException e) {
            log.error("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }
}
