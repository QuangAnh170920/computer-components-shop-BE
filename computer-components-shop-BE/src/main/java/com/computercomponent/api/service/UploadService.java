package com.computercomponent.api.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadService {
    String storeFile(MultipartFile file) throws IOException;
}
