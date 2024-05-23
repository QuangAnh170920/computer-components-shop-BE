package com.computercomponent.api.controller;

import com.computercomponent.api.dto.OrdersDTO;
import com.computercomponent.api.dto.OrdersUpdateRequestDTO;
import com.computercomponent.api.dto.ProductUpdateRequestDTO;
import com.computercomponent.api.model.ResponseWrapper;
import com.computercomponent.api.request.OrdersDetailRequest;
import com.computercomponent.api.request.OrdersRequest;
import com.computercomponent.api.request.ProductDetailRequest;
import com.computercomponent.api.request.ProductsRequest;
import com.computercomponent.api.service.OrdersService;
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
@RequestMapping("v1/admin/orders")
@SecurityRequirement(name = "computer-components-admin-security")
@PreAuthorize("{@ComputerComponentShopAuthorizer.authorize(authentication)}")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    @Operation(summary = "Tạo mới đơn hàng", description = "Tạo mới đơn hàng")
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> createProd(@RequestBody OrdersDTO ordersDTO) {
        return ResponseEntity.ok(new ResponseWrapper(ordersService.createOrder(ordersDTO)));
    }

    @Operation(summary = "Tìm kiếm và danh sách đơn hàng", description = "Tìm kiếm và danh sách đơn hàng")
    @PostMapping(value = "/find-all-and-search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> findAllAndSearch(@RequestBody OrdersRequest ordersRequest) {
        return ResponseEntity.ok(new ResponseWrapper(ordersService.getList(ordersRequest)));
    }

    @Operation(summary = "Cập nhật đơn hàng", description = "Cập nhật đơn hàng")
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> update(@RequestBody @Valid OrdersUpdateRequestDTO ordersUpdateRequestDTO) {
        return ResponseEntity.ok(new ResponseWrapper(ordersService.update(ordersUpdateRequestDTO)));
    }

    @Operation(summary = "Xóa đơn hàng", description = "Xóa đơn hàng ")
    @PostMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(new ResponseWrapper(ordersService.delete(id)));
    }

    @Operation(summary = "thông tin chi tiết đơn hàng", description = "thông tin chi tiết đơn hàng")
    @PostMapping(value = "/get-detail", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> getDetail(@RequestBody OrdersDetailRequest ordersDetailRequest) {
        return ResponseEntity.ok(new ResponseWrapper(ordersService.getDetail(ordersDetailRequest.getId())));
    }
}
