package com.computercomponent.api.service;

import com.computercomponent.api.dto.ProductReviewDTO;
import com.computercomponent.api.dto.ProductReviewManagementDTO;
import com.computercomponent.api.request.ProductReviewRequest;
import com.computercomponent.api.response.ProductReviewDetail;
import org.springframework.data.domain.Page;

public interface ProductReviewService {
    String createPR(ProductReviewDTO productReviewDTO);
    Page<ProductReviewManagementDTO> getList(ProductReviewRequest productReviewRequest);
    String updatePR(ProductReviewManagementDTO productReviewManagementDTO);
    String deletePR(Long id);
    ProductReviewDetail getDetail(Long id);
}
