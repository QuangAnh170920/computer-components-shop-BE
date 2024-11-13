package com.computercomponent.api.service;

import com.computercomponent.api.dto.*;
import com.computercomponent.api.request.PromotionRequest;
import com.computercomponent.api.response.PromotionDetail;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PromotionService {
    String create(PromotionDTO promotionDTO);
    Page<PromotionManagementDTO> getList(PromotionRequest promotionRequest);

    String update(PromotionManagementDTO promotionManagementDTO);

    String delete(Long id);

    PromotionDetail getDetail(Long id);

    PromotionManagementStatusDTO updateStatus(PromotionManagementStatusDTO promotionManagementStatusDTO);

    List<PromotionDropListDTO> dropList();
}
