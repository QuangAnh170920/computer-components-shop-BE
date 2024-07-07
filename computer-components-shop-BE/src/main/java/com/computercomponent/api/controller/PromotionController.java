package com.computercomponent.api.controller;

import com.computercomponent.api.dto.*;
import com.computercomponent.api.model.ResponseWrapper;
import com.computercomponent.api.request.BrandRequest;
import com.computercomponent.api.request.PromotionRequest;
import com.computercomponent.api.service.PromotionService;
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
@RequestMapping("/v1/api/admin/promotion")
@SecurityRequirement(name = "computer-components-admin-security")
@PreAuthorize("{@ComputerComponentShopAuthorizer.authorize(authentication)}")
public class PromotionController {
    @Autowired
    protected PromotionService promotionService;

    @Operation(summary = "Tạo mới khuyến mãi", description = "Tạo mới khuyến mãi")
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> createBrand(@RequestBody PromotionDTO promotionDTO) {
        return ResponseEntity.ok(new ResponseWrapper(promotionService.create(promotionDTO)));
    }

    @Operation(summary = "Tìm kiếm và danh sách khuyến mãi", description = "Tìm kiếm và danh sách khuyến mãi")
    @PostMapping(value = "/find-all-and-search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> findAllAndSearchBrand(@RequestBody PromotionRequest promotionRequest) {
        return ResponseEntity.ok(new ResponseWrapper(promotionService.getList(promotionRequest)));
    }

    @Operation(summary = "Cập nhật khuyến mãi", description = "Cập nhật thương hiệu khuyến mãi")
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> updateBrand(@RequestBody @Valid PromotionManagementDTO promotionManagementDTO) {
        return ResponseEntity.ok(new ResponseWrapper(promotionService.update(promotionManagementDTO)));
    }

    @Operation(summary = "Cập nhật status khuyến mãi", description = "Cập nhật status khuyến mãi")
    @PutMapping(value = "/update-status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> updateStatusBrand(@RequestBody @Valid PromotionManagementStatusDTO promotionManagementStatusDTO) {
        return ResponseEntity.ok(new ResponseWrapper(promotionService.updateStatus(promotionManagementStatusDTO)));
    }

    @Operation(summary = "Xóa khuyến mãi", description = "Xóa khuyến mãi")
    @PostMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> deleteBu(@PathVariable("id") Long id) {
        return ResponseEntity.ok(new ResponseWrapper(promotionService.delete(id)));
    }

    @Operation(summary = "thông tin chi tiết khuyến mãi", description = "thông tin chi tiết khuyến mãi")
    @PostMapping(value = "/get-detail/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> getDetail(@PathVariable("id") Long id) {
        return ResponseEntity.ok(new ResponseWrapper(promotionService.getDetail(id)));
    }
}
