package com.computercomponent.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("v1/admin/products")
@SecurityRequirement(name = "computer-components-admin-security")
@PreAuthorize("{@ComputerComponentShopAuthorizer.authorize(authentication)}")
public class ProductsController {
}
