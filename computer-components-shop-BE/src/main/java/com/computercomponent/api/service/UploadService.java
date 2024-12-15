package com.computercomponent.api.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface UploadService {
    Map<String, String> storeFile(MultipartFile file) throws IOException;
}
