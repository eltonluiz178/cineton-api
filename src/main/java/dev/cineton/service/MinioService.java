package dev.cineton.service;

import org.springframework.web.multipart.MultipartFile;

public interface MinioService {
    String uploadFile(String bucketName, String fileName, MultipartFile file);
    String getPresignedUrl(String bucketName, String fileName);
}