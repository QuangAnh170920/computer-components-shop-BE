package com.computercomponent.api.controller;

import com.computercomponent.api.dto.BrandDTO;
import com.computercomponent.api.dto.BrandManagementDTO;
import com.computercomponent.api.model.ResponseWrapper;
import com.computercomponent.api.request.BrandRequest;
import com.computercomponent.api.service.BrandService;
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
@RequestMapping("/v1/api/admin/brand")
@SecurityRequirement(name = "computer-components-admin-security")
@PreAuthorize("{@ComputerComponentShopAuthorizer.authorize(authentication)}")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @Operation(summary = "Tạo mới thương hiệu", description = "Tạo mới thương hiệu")
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> createBrand(@RequestBody BrandDTO BrandDTO) {
        return ResponseEntity.ok(new ResponseWrapper(brandService.createBrand(BrandDTO)));
    }

    @Operation(summary = "Tìm kiếm và danh sách thương hiệu", description = "Tìm kiếm và danh sách thương hiệu")
    @PostMapping(value = "/find-all-and-search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> findAllAndSearchBrand(@RequestBody BrandRequest brandRequest) {
        return ResponseEntity.ok(new ResponseWrapper(brandService.getBrandList(brandRequest)));
    }

    @Operation(summary = "Cập nhật thương hiệu", description = "Cập nhật thương hiệu thương hiệu")
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> updateBrand(@RequestBody @Valid BrandManagementDTO brandManagementDTO) {
        return ResponseEntity.ok(new ResponseWrapper(brandService.updateBrand(brandManagementDTO)));
    }

    @Operation(summary = "Xóa thương hiệu", description = "Xóa thương hiệu")
    @PostMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> deleteBu(@PathVariable("id") Long id) {
        return ResponseEntity.ok(new ResponseWrapper(brandService.deleteBrand(id)));
    }
}
