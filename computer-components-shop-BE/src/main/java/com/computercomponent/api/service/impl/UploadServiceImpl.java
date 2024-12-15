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
        Path uploadFolder = Paths.get("C:\\DoAn\\computer-components-shop-FE\\computer-components-shop-FE\\src\\assets\\layout\\images"); // Thư mục lưu trữ ảnh

        // Tạo thư mục nếu chưa tồn tại
        if (!Files.exists(uploadFolder)) {
            Files.createDirectories(uploadFolder);
        }

        // Lưu file
        Path filePath = uploadFolder.resolve(fileName);
        Files.copy(file.getInputStream(), filePath);

        // Tạo đường dẫn URL dạng "file://"
        String fileUrl = filePath.toString().replace("\\", "/");

        // Trả về JSON response
        Map<String, String> response = new HashMap<>();
        response.put("url", fileUrl);
        response.put("message", "SUCCESS");
        return response;
    }
}
