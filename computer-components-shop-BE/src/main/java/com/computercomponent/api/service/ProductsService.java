package com.computercomponent.api.service;

import com.computercomponent.api.dto.ProductsDTO;
import com.computercomponent.api.dto.ProductsManagementDTO;
import com.computercomponent.api.request.ProductsRequest;
import com.computercomponent.api.response.ProductDetail;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductsService {
    String createProduct(ProductsDTO productsDTO);
    Page<ProductsManagementDTO> getProductsList(ProductsRequest productsRequest);

    ProductsManagementDTO updateProduct(ProductsManagementDTO productsManagementDTO);

    String deleteProduct(Long id);

    ProductDetail getDetail(Long id);
}
