package com.computercomponent.api.service;

import com.computercomponent.api.dto.BrandDTO;
import com.computercomponent.api.dto.BrandManagementDTO;
import com.computercomponent.api.request.BrandRequest;
import org.springframework.data.domain.Page;

public interface BrandService {
    String createBrand(BrandDTO brandDTO);
    Page<BrandManagementDTO> getBrandList(BrandRequest brandRequest);

    BrandManagementDTO updateBrand(BrandManagementDTO brandManagementDTO);

    String deleteBrand(Long id);
}
