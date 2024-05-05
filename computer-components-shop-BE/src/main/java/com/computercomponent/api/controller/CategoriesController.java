package com.computercomponent.api.controller;

import com.computercomponent.api.dto.BrandDTO;
import com.computercomponent.api.dto.BrandManagementDTO;
import com.computercomponent.api.dto.CategoriesDTO;
import com.computercomponent.api.dto.CategoriesManagementDTO;
import com.computercomponent.api.model.ResponseWrapper;
import com.computercomponent.api.request.BrandRequest;
import com.computercomponent.api.request.CategoriesRequest;
import com.computercomponent.api.service.CategoriesService;
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
@RequestMapping("/v1/api/admin/categories")
@SecurityRequirement(name = "computer-components-admin-security")
@PreAuthorize("{@ComputerComponentShopAuthorizer.authorize(authentication)}")
public class CategoriesController {
    @Autowired
    private CategoriesService categoriesService;

    @Operation(summary = "Tạo mới 1 loại sản phẩm", description = "Tạo mới 1 loại sản phẩm")
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> createCate(@RequestBody CategoriesDTO categoriesDTO) {
        return ResponseEntity.ok(new ResponseWrapper(categoriesService.createCate(categoriesDTO)));
    }

    @Operation(summary = "Tìm kiếm và danh sách loại sản phẩm", description = "Tìm kiếm và danh sách loại sản phẩm")
    @PostMapping(value = "/find-all-and-search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> findAllAndSearchBrand(@RequestBody CategoriesRequest categoriesRequest) {
        return ResponseEntity.ok(new ResponseWrapper(categoriesService.getCateList(categoriesRequest)));
    }

    @Operation(summary = "Cập nhật loại sản phẩm", description = "Cập nhật loại sản phẩm ")
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> updateBrand(@RequestBody @Valid CategoriesManagementDTO categoriesManagementDTO) {
        return ResponseEntity.ok(new ResponseWrapper(categoriesService.updateCate(categoriesManagementDTO)));
    }

    @Operation(summary = "Xóa loại sản phẩm", description = "Xóa loại sản phẩm ")
    @PostMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> deleteBu(@PathVariable("id") Long id) {
        return ResponseEntity.ok(new ResponseWrapper(categoriesService.deleteCate(id)));
    }
}
