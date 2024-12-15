package com.computercomponent.api.service;

import com.computercomponent.api.dto.*;
import com.computercomponent.api.request.ProductsRequest;
import com.computercomponent.api.response.ProductDetail;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductsService {
    String createProduct(ProductsDTO productsDTO);

    Page<ProductsManagementDTO> getProductsList(ProductsRequest productsRequest);

    ProductUpdateRequestDTO updateProduct(ProductUpdateRequestDTO ProductUpdateRequestDTO);

    String deleteProduct(Long id);

    ProductDetail getDetail(Long id);

    List<ProductDropListDTO> dropList();

    Page<ProductWarehouseDTO> getProductsQuantityList(ProductsRequest productsRequest);

    ProductManagementStatusDTO updateStatus(ProductManagementStatusDTO productManagementStatusDTO);
}
