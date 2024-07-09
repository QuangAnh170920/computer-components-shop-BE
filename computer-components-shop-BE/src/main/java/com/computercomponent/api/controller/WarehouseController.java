package com.computercomponent.api.controller;

import com.computercomponent.api.dto.*;
import com.computercomponent.api.model.ResponseWrapper;
import com.computercomponent.api.request.PromotionRequest;
import com.computercomponent.api.request.WarehouseRequest;
import com.computercomponent.api.service.WarehouseService;
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
@RequestMapping("/v1/api/admin/warehouse")
@SecurityRequirement(name = "computer-components-admin-security")
@PreAuthorize("{@ComputerComponentShopAuthorizer.authorize(authentication)}")
public class WarehouseController {
    @Autowired
    private WarehouseService warehouseService;

    @Operation(summary = "Tạo mới đơn hàng", description = "Tạo mới đơn hàng")
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> createBrand(@RequestBody WarehouseDTO warehouseDTO) {
        return ResponseEntity.ok(new ResponseWrapper(warehouseService.create(warehouseDTO)));
    }

    @Operation(summary = "Tìm kiếm và danh sách đơn hàng", description = "Tìm kiếm và danh sách đơn hàng")
    @PostMapping(value = "/find-all-and-search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> findAllAndSearchBrand(@RequestBody WarehouseRequest warehouseRequest) {
        return ResponseEntity.ok(new ResponseWrapper(warehouseService.getList(warehouseRequest)));
    }

    @Operation(summary = "Cập nhật đơn hàng", description = "Cập nhật thương hiệu đơn hàng")
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> updateBrand(@RequestBody @Valid WarehouseManagementDTO warehouseManagementDTO) {
        return ResponseEntity.ok(new ResponseWrapper(warehouseService.update(warehouseManagementDTO)));
    }

    @Operation(summary = "Cập nhật status đơn hàng", description = "Cập nhật status đơn hàng")
    @PutMapping(value = "/update-status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> updateStatusBrand(@RequestBody @Valid WarehouseManagementStatusDTO warehouseManagementStatusDTO) {
        return ResponseEntity.ok(new ResponseWrapper(warehouseService.updateStatus(warehouseManagementStatusDTO)));
    }

    @Operation(summary = "Xóa đơn hàng", description = "Xóa đơn hàng")
    @PostMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> deleteBu(@PathVariable("id") Long id) {
        return ResponseEntity.ok(new ResponseWrapper(warehouseService.delete(id)));
    }

    @Operation(summary = "thông tin chi tiết đơn hàng", description = "thông tin chi tiết đơn hàng")
    @PostMapping(value = "/get-detail/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> getDetail(@PathVariable("id") Long id) {
        return ResponseEntity.ok(new ResponseWrapper(warehouseService.getDetail(id)));
    }
}
