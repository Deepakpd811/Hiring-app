package com.bridgelab.hiringapp.utils;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtil {


    private final static String UPLOAD_PATH = "uploads/document";

    public static String save(MultipartFile file)  {

        try {
            // Create directory if not exists
            Path uploadPath = Paths.get(UPLOAD_PATH);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generate unique filename
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.'));
            String uniqueFileName = System.currentTimeMillis() + fileExtension;

            Path filePath = uploadPath.resolve(uniqueFileName);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return filePath.toString(); // Or return a relative/public URL path if needed

        } catch (IOException e) {
            throw new RuntimeException("File storage failed", e);
        }
    }


}
