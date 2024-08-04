package com.computercomponent.api.service;

import com.computercomponent.api.dto.BrandDTO;
import com.computercomponent.api.dto.BrandDropListDTO;
import com.computercomponent.api.dto.BrandManagementDTO;
import com.computercomponent.api.dto.BrandManagementStatusDTO;
import com.computercomponent.api.request.BrandRequest;
import com.computercomponent.api.response.BrandDetail;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BrandService {
    String createBrand(BrandDTO brandDTO);

    Page<BrandManagementDTO> getBrandList(BrandRequest brandRequest);

    String updateBrand(BrandManagementDTO brandManagementDTO);

    String deleteBrand(Long id);

    List<BrandDropListDTO> dropList();

    BrandDetail getDetail(Long id);

    BrandManagementStatusDTO updateStatus(BrandManagementStatusDTO brandManagementStatusDTO);
}
