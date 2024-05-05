package com.computercomponent.api.service;

import com.computercomponent.api.dto.CategoriesDTO;
import com.computercomponent.api.dto.CategoriesManagementDTO;
import com.computercomponent.api.request.CategoriesRequest;
import org.springframework.data.domain.Page;

public interface CategoriesService {
    String createCate(CategoriesDTO categoriesDTO);
    Page<CategoriesManagementDTO> getCateList(CategoriesRequest categoriesRequest);

    CategoriesManagementDTO updateCate(CategoriesManagementDTO categoriesManagementDTO);

    String deleteCate(Long id);
}
