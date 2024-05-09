package com.computercomponent.api.controller;

import com.computercomponent.api.dto.ProductImageDTO;
import com.computercomponent.api.dto.ProductImageManagementDTO;
import com.computercomponent.api.dto.ProductsDTO;
import com.computercomponent.api.dto.ProductsManagementDTO;
import com.computercomponent.api.model.ResponseWrapper;
import com.computercomponent.api.request.ProductImageRequest;
import com.computercomponent.api.request.ProductsRequest;
import com.computercomponent.api.service.ProductImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/v1/api/admin/product-image")
@SecurityRequirement(name = "computer-components-admin-security")
@PreAuthorize("{@ComputerComponentShopAuthorizer.authorize(authentication)}")
public class ProductImageController {
    @Autowired
    private ProductImageService productImageService;

    @Operation(summary = "Tạo mới ảnh sản phẩm", description = "Tạo mới ảnh sản phẩm")
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> createProd(@RequestBody ProductImageDTO productImageDTO) {
        return ResponseEntity.ok(new ResponseWrapper(productImageService.createProductImage(productImageDTO)));
    }

    @Operation(summary = "Tìm kiếm và danh sách ảnh sản phẩm", description = "Tìm kiếm và danh sách ảnh sản phẩm")
    @PostMapping(value = "/find-all-and-search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> findAllAndSearch(@RequestBody ProductImageRequest productImageRequest) {
        return ResponseEntity.ok(new ResponseWrapper(productImageService.getProductImageList(productImageRequest)));
    }

    @Operation(summary = "Cập nhật ảnh sản phẩm", description = "Cập nhật ảnh sản phẩm ")
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> update(@RequestBody @Valid ProductImageManagementDTO productImageManagementDTO) {
        return ResponseEntity.ok(new ResponseWrapper(productImageService.updateProductImage(productImageManagementDTO)));
    }

    @Operation(summary = "Xóa ảnh sản phẩm", description = "Xóa ảnh sản phẩm ")
    @PostMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(new ResponseWrapper(productImageService.deleteProductImage(id)));
    }
}
