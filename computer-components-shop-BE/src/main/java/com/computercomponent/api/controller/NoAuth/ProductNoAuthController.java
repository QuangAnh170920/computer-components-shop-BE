package com.computercomponent.api.controller.NoAuth;

import com.computercomponent.api.model.ResponseWrapper;
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

@Slf4j
@RestController
@RequestMapping("v1/api/user/products-no-auth")
public class ProductNoAuthController {
    @Autowired
    private ProductsService productsService;

    @Operation(summary = "Tìm kiếm và danh sách sản phẩm theo điều kiện", description = "Tìm kiếm và danh sách sản phẩm")
    @PostMapping(value = "/find-all-and-search-condition/{category_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> findAllAndSearch(@PathVariable("category_id") Long categoryId) {
        return ResponseEntity.ok(new ResponseWrapper(productsService.getProductsListNoAuth(categoryId)));
    }

    @Operation(summary = "thông tin chi tiết sản phẩm", description = "thông tin chi tiết sản phẩm")
    @PostMapping(value = "/get-detail/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> getDetail(@PathVariable("id") Long id) {
        return ResponseEntity.ok(new ResponseWrapper(productsService.getDetailNoAuth(id)));
    }
}
