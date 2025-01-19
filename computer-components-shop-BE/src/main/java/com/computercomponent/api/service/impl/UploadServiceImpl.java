package com.computercomponent.api.service.impl;

import com.computercomponent.api.common.Const;
import com.computercomponent.api.service.UploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UploadServiceImpl implements UploadService {
    @Value("${upload.folder}")
    private Path uploadFolder;


    @Override
    public Map<String, String> storeFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        Path primaryFolder = Paths.get("C:\\DoAn\\computer-components-shop-FE\\computer-components-shop-FE\\src\\assets\\layout\\images");

        Path secondaryFolder = Paths.get("C:\\DoAn\\computer-components-shop-FE-member-site\\computer-components-shop-FE-member-site\\src\\assets\\layout\\images");

        saveFileToFolder(file, primaryFolder, fileName);
        saveFileToFolder(file, secondaryFolder, fileName);

        String fileUrl = primaryFolder.resolve(fileName).toString().replace("\\", "/");
        Map<String, String> response = new HashMap<>();
        response.put("url", fileUrl);
        response.put("message", "SUCCESS");
        return response;
    }

    private void saveFileToFolder(MultipartFile file, Path folderPath, String fileName) throws IOException {
        if (!Files.exists(folderPath)) {
            Files.createDirectories(folderPath);
        }

        Path filePath = folderPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath);
    }
}
