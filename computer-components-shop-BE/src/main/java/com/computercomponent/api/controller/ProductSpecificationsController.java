package com.computercomponent.api.controller;

import com.computercomponent.api.dto.ProductSpecificationsDTO;
import com.computercomponent.api.dto.ProductSpecificationsManagementDTO;
import com.computercomponent.api.dto.ProductsDTO;
import com.computercomponent.api.dto.ProductsManagementDTO;
import com.computercomponent.api.model.ResponseWrapper;
import com.computercomponent.api.request.ProductSpecificationsRequest;
import com.computercomponent.api.request.ProductsRequest;
import com.computercomponent.api.service.ProductSpecificationsService;
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
@RequestMapping("v1/admin/product-specifications")
@SecurityRequirement(name = "computer-components-admin-security")
@PreAuthorize("{@ComputerComponentShopAuthorizer.authorize(authentication)}")
public class ProductSpecificationsController {
    @Autowired
    private ProductSpecificationsService productSpecificationsService;

    @Operation(summary = "Tạo mới thông số sản phẩm", description = "Tạo mới 1 thông số sản phẩm")
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> create(@RequestBody ProductSpecificationsDTO productSpecificationsDTO) {
        return ResponseEntity.ok(new ResponseWrapper(productSpecificationsService.createProductSpec(productSpecificationsDTO)));
    }

    @Operation(summary = "Tìm kiếm và danh sách thông số sản phẩm", description = "Tìm kiếm và danh sách thông số sản phẩm")
    @PostMapping(value = "/find-all-and-search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> findAllAndSearch(@RequestBody ProductSpecificationsRequest productSpecificationsRequest) {
        return ResponseEntity.ok(new ResponseWrapper(productSpecificationsService.getProductSpecList(productSpecificationsRequest)));
    }

    @Operation(summary = "Cập nhật thông số sản phẩm", description = "Cập nhật thông số sản phẩm ")
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> update(@RequestBody @Valid ProductSpecificationsManagementDTO productSpecificationsManagementDTO) {
        return ResponseEntity.ok(new ResponseWrapper(productSpecificationsService.updateProductSpec(productSpecificationsManagementDTO)));
    }

    @Operation(summary = "Xóa thông số sản phẩm", description = "Xóa thông số sản phẩm ")
    @PostMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(new ResponseWrapper(productSpecificationsService.deleteProductSpec(id)));
    }
}
