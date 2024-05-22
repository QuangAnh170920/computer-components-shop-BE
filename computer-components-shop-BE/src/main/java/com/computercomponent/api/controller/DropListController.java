package com.computercomponent.api.controller;

import com.computercomponent.api.model.ResponseWrapper;
import com.computercomponent.api.service.BrandService;
import com.computercomponent.api.service.CategoriesService;
import com.computercomponent.api.service.ProductsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("v1/admin/drop-list")
@SecurityRequirement(name = "computer-components-admin-security")
@PreAuthorize("{@ComputerComponentShopAuthorizer.authorize(authentication)}")
public class DropListController {
    @Autowired
    private CategoriesService categoriesService;

    @Autowired
    private ProductsService productsService;

    @Autowired
    private BrandService brandService;

    @Operation(summary = "drop list loại sản phẩm", description = "drop list loại sản phẩm")
    @GetMapping(value = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> categoryDropList() {
        return ResponseEntity.ok(new ResponseWrapper(categoriesService.dropList()));
    }

    @Operation(summary = "drop list thương hiệu sản phẩm", description = "drop list thương hiệu sản phẩm")
    @GetMapping(value = "/brand", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> brandDropList() {
        return ResponseEntity.ok(new ResponseWrapper(brandService.dropList()));
    }

    @Operation(summary = "drop list sản phẩm", description = "drop list sản phẩm")
    @GetMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> productsDropList() {
        return ResponseEntity.ok(new ResponseWrapper(productsService.dropList()));
    }
}
