package com.computercomponent.api.controller;

import com.computercomponent.api.dto.PromotionDTO;
import com.computercomponent.api.model.ResponseWrapper;
import com.computercomponent.api.service.UploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/v1/api/admin/upload")
@SecurityRequirement(name = "computer-components-admin-security")
@PreAuthorize("{@ComputerComponentShopAuthorizer.authorize(authentication)}")
public class UploadController {
    @Autowired
    private UploadService uploadService;

    @Operation(summary = "Upload ảnh", description = "Upload ảnh")
    @PostMapping(value = "/upload", produces = MediaType.APPLICATION_JSON_VALUE, consumes = "multipart/form-data")
    public ResponseEntity<ResponseWrapper> upload(@RequestParam("file") MultipartFile file) {
        try {
            String result = uploadService.storeFile(file);
            return ResponseEntity.ok(new ResponseWrapper(result));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper("Failed to upload file: " + e.getMessage()));
        }
    }
}
