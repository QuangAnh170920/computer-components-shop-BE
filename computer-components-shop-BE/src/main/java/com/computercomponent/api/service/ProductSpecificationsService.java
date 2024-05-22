package com.computercomponent.api.service;

import com.computercomponent.api.dto.ProductSpecificationsDTO;
import com.computercomponent.api.dto.ProductSpecificationsManagementDTO;
import com.computercomponent.api.dto.ProductSpecificationsUpdateRequestDTO;
import com.computercomponent.api.request.ProductSpecificationsRequest;
import org.springframework.data.domain.Page;

public interface ProductSpecificationsService {
    String createProductSpec(ProductSpecificationsDTO productSpecificationsDTO);
    Page<ProductSpecificationsManagementDTO> getProductSpecList(ProductSpecificationsRequest productSpecificationsRequest);
    ProductSpecificationsUpdateRequestDTO updateProductSpec(ProductSpecificationsUpdateRequestDTO productSpecificationsUpdateRequestDTO);
    String deleteProductSpec(Long id);
}
