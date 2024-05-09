package com.computercomponent.api.service;

import com.computercomponent.api.dto.ProductImageDTO;
import com.computercomponent.api.dto.ProductImageManagementDTO;
import com.computercomponent.api.request.ProductImageRequest;
import org.springframework.data.domain.Page;

public interface ProductImageService {
    String createProductImage(ProductImageDTO productImageDTO);
    Page<ProductImageManagementDTO> getProductImageList(ProductImageRequest productImageRequest);

    ProductImageManagementDTO updateProductImage(ProductImageManagementDTO productImageManagementDTO);

    String deleteProductImage(Long id);
}
