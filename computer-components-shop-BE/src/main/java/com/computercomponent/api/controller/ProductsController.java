package com.computercomponent.api.controller;

import com.computercomponent.api.dto.CategoriesDTO;
import com.computercomponent.api.dto.CategoriesManagementDTO;
import com.computercomponent.api.dto.ProductsDTO;
import com.computercomponent.api.dto.ProductsManagementDTO;
import com.computercomponent.api.model.ResponseWrapper;
import com.computercomponent.api.request.CategoriesRequest;
import com.computercomponent.api.request.ProductsRequest;
import com.computercomponent.api.service.ProductsService;
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
@RequestMapping("v1/admin/products")
@SecurityRequirement(name = "computer-components-admin-security")
@PreAuthorize("{@ComputerComponentShopAuthorizer.authorize(authentication)}")
public class ProductsController {
    @Autowired
    private ProductsService productsService;

    @Operation(summary = "Tạo mới 1 sản phẩm", description = "Tạo mới 1 sản phẩm")
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> createProd(@RequestBody ProductsDTO productsDTO) {
        return ResponseEntity.ok(new ResponseWrapper(productsService.createProduct(productsDTO)));
    }

    @Operation(summary = "Tìm kiếm và danh sách sản phẩm", description = "Tìm kiếm và danh sách loại sản phẩm")
    @PostMapping(value = "/find-all-and-search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> findAllAndSearch(@RequestBody ProductsRequest productsRequest) {
        return ResponseEntity.ok(new ResponseWrapper(productsService.getProductsList(productsRequest)));
    }

    @Operation(summary = "Cập nhật sản phẩm", description = "Cập nhật sản phẩm ")
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> update(@RequestBody @Valid ProductsManagementDTO productsManagementDTO) {
        return ResponseEntity.ok(new ResponseWrapper(productsService.updateProduct(productsManagementDTO)));
    }

    @Operation(summary = "Xóa sản phẩm", description = "Xóa sản phẩm ")
    @PostMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(new ResponseWrapper(productsService.deleteProduct(id)));
    }
}
