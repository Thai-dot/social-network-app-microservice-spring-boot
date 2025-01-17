package com.thaidot.file_service.service;

import com.thaidot.file_service.enums.FileTypeUpload;
import com.thaidot.file_service.exception.AppException;
import com.thaidot.file_service.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UploadFileService {

    @Value("${file.size.max}")
    private long MAX_FILE_SIZE; // 5 MB

    @Value("${file.static-path}")
    private String mainPath;

    @Value("${file.upload-dir.avatar}")
    private String avatarUploadDir;

    public String uploadFile(String userId, MultipartFile file, String uploadDir, FileTypeUpload fileType) {
        try {
            // Validate file type and size
            validateFile(file, fileType);

            // Get the absolute path to the static folder in the classpath
            Path staticPath = Paths.get(mainPath, avatarUploadDir);
            File directory = staticPath.toFile();
            if (!directory.exists()) {
                directory.mkdirs(); // Create the directory if it doesn't exist
            }

            // Generate a unique file name
            String fileName = userId + "_" + UUID.randomUUID() + ".jpg";

            // Save the file
            Path filePath = staticPath.resolve(fileName);
            Files.write(filePath, file.getBytes());

            // Return the path relative to the application for access via HTTP
            String relativePath = avatarUploadDir + fileName;
            return relativePath;

        } catch (IOException ex) {
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    // make a method to delete file in the static folder
    public void deleteFile(String path) {
        try {
            Path filePath = Paths.get(mainPath, path);
            Files.delete(filePath);
        } catch (IOException ex) {
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    private void validateFile(MultipartFile file, FileTypeUpload fileType) {
        if (file.isEmpty()) {
            throw new AppException(ErrorCode.FILE_EMPTY);
        }

        if (file.getSize() > MAX_FILE_SIZE) { // 5 MB
            throw new AppException(ErrorCode.INVALID_FILE_SIZE);
        }

        if (fileType.getType() == FileTypeUpload.IMAGE.getType() && !file.getContentType().startsWith("image/")) {
            throw new AppException(ErrorCode.INVALID_IMAGE_FILE);
        }
    }
}