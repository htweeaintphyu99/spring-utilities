package com.tutorial.awss3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.waiters.AmazonS3Waiters;
import com.amazonaws.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StorageService {

    @Value("${credentials.aws_bucket-name}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;

    public List<String> listObjects() {
        ObjectListing objectListing = s3Client.listObjects(bucketName);
        return objectListing.getObjectSummaries()
                .stream()
                .map(S3ObjectSummary::getKey)
                .collect(Collectors.toList());
    }

    public String createFolder(String folderName) {
        try {
            // S3 doesn't have an explicit concept of folders, so we create an empty object with the folder path as the key
            String folderKey = folderName.endsWith("/") ? folderName : folderName + "/";

            // Create an empty file
            File emptyFile = File.createTempFile("empty", null);
            emptyFile.deleteOnExit();

            s3Client.putObject(new PutObjectRequest(bucketName, folderKey, emptyFile));
            return "Folder created successfully.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error creating folder.";
        }
    }

    public String uploadFile(String dirName, MultipartFile file) {

        File fileObj = convertMultipartFileToFile(file);
        String fileName = System.currentTimeMillis()+"_"+file.getOriginalFilename();
        s3Client.putObject(new PutObjectRequest(bucketName, dirName+"/"+fileName, fileObj));
        fileObj.delete();
        return "File uploaded successfully : " + fileName;
    }

    public byte[] downloadFile(String fileName) {
        S3Object s3Object = s3Client.getObject(bucketName, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            byte[] content = IOUtils.toByteArray(inputStream);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String deleteFile(String fileName) {
        s3Client.deleteObject(bucketName, fileName);
        return fileName + " is removed...";
    }

    public List<String> deleteFolder(String folderName) {
        List<String> objectKeys = new ArrayList<>();

        // Ensure folderName ends with "/"
        if (!folderName.endsWith("/")) {
            folderName += "/";
        }

        // List objects in the specified "folder"
        ObjectListing objectListing = s3Client.listObjects(bucketName, folderName);

        // Iterate over object summaries and collect keys and delete each object
        for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            objectKeys.add(objectSummary.getKey());
            s3Client.deleteObject(new DeleteObjectRequest(bucketName, objectSummary.getKey()));
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
