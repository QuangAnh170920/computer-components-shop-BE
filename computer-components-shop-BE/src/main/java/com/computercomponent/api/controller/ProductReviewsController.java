package com.computercomponent.api.controller;

import com.computercomponent.api.dto.BrandDTO;
import com.computercomponent.api.dto.BrandManagementDTO;
import com.computercomponent.api.dto.ProductReviewDTO;
import com.computercomponent.api.dto.ProductReviewManagementDTO;
import com.computercomponent.api.model.ResponseWrapper;
import com.computercomponent.api.request.BrandRequest;
import com.computercomponent.api.request.ProductReviewRequest;
import com.computercomponent.api.service.ProductReviewService;
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
@RequestMapping("/v1/api/admin/product-reviews")
@SecurityRequirement(name = "computer-components-admin-security")
@PreAuthorize("{@ComputerComponentShopAuthorizer.authorize(authentication)}")
public class ProductReviewsController {
    @Autowired
    private ProductReviewService productReviewService;

    @Operation(summary = "Tạo mới đánh giá sản phẩm", description = "Tạo mới đánh giá sản phẩm")
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> create(@RequestBody ProductReviewDTO productReviewDTO) {
        return ResponseEntity.ok(new ResponseWrapper(productReviewService.createPR(productReviewDTO)));
    }

    @Operation(summary = "Cập nhật đánh giá sản phẩm", description = "Cập nhật đánh giá sản phẩm")
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> updateBrand(@RequestBody @Valid ProductReviewManagementDTO productReviewManagementDTO) {
        return ResponseEntity.ok(new ResponseWrapper(productReviewService.updatePR(productReviewManagementDTO)));
    }

    @Operation(summary = "Tìm kiếm và danh sách đánh giá sản phẩm", description = "Tìm kiếm và danh sách đánh giá sản phẩm")
    @PostMapping(value = "/find-all-and-search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> findAllAndSearchBrand(@RequestBody ProductReviewRequest productReviewRequest) {
        return ResponseEntity.ok(new ResponseWrapper(productReviewService.getList(productReviewRequest)));
    }

    @Operation(summary = "Xóa đánh giá sản phẩm", description = "Xóa đánh giá sản phẩm")
    @PostMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> deleteBu(@PathVariable("id") Long id) {
        return ResponseEntity.ok(new ResponseWrapper(productReviewService.deletePR(id)));
    }

    @Operation(summary = "thông tin chi tiết đánh giá sản phẩm", description = "thông tin chi tiết đánh giá sản phẩm")
    @PostMapping(value = "/get-detail/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> getDetail(@PathVariable("id") Long id) {
        return ResponseEntity.ok(new ResponseWrapper(productReviewService.getDetail(id)));
    }
}
