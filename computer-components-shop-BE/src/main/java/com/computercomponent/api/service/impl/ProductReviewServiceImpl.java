package com.computercomponent.api.service.impl;

import com.computercomponent.api.common.Const;
import com.computercomponent.api.dto.ProductReviewDTO;
import com.computercomponent.api.dto.ProductReviewManagementDTO;
import com.computercomponent.api.dto.ProductReviewUpdateManagementDTO;
import com.computercomponent.api.entity.ProductReviews;
import com.computercomponent.api.repository.ProductReviewRepository;
import com.computercomponent.api.request.ProductReviewRequest;
import com.computercomponent.api.response.ProductReviewDetail;
import com.computercomponent.api.service.ProductReviewService;
import com.computercomponent.api.until.DataUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Objects;

@Service
public class ProductReviewServiceImpl implements ProductReviewService {
    @Autowired
    private ProductReviewRepository productReviewRepository;

    @Override
    public String createPR(ProductReviewDTO productReviewDTO) {
        ProductReviews productReviews = new ProductReviews();
        BeanUtils.copyProperties(productReviewDTO, productReviews);
        productReviewRepository.save(productReviews);
        return Const.MESSAGE_CODE.SUCCESS;
    }

    @Override
    public Page<ProductReviewManagementDTO> getList(ProductReviewRequest productReviewRequest) {
        PageRequest pageRequest = DataUtil.getPageable(productReviewRequest.getPageNumber(), productReviewRequest.getPageSize());
        if (productReviewRequest.getProductId() != null) {
            Assert.notNull(productReviewRequest.getProductId(), Const.PRODUCTS.PROD_NOT_FOUND);
        }
        if (productReviewRequest.getUserId() != null) {
            Assert.notNull(productReviewRequest.getUserId(), Const.USER.USER_NOT_FOUND);
        }
        return productReviewRepository.findAllAndSearch(productReviewRequest.getProductId(), productReviewRequest.getUserId(), pageRequest);
    }

    @Override
    public String updatePR(ProductReviewUpdateManagementDTO productReviewUpdateManagementDTO) {
        ProductReviews productReviews = productReviewRepository.findProductReviewsById(productReviewUpdateManagementDTO.getId());
        Assert.isTrue(productReviews != null, Const.PRODUCT_REVIEW.PR_NOT_FOUND);

        if (productReviewUpdateManagementDTO.getUserId() != null && !Objects.equals(productReviews.getUserId(), productReviewUpdateManagementDTO.getUserId())) {
            productReviews.setUserId(productReviewUpdateManagementDTO.getUserId());
        }
        if (productReviewUpdateManagementDTO.getProductId() != null && !Objects.equals(productReviews.getProductId(), productReviewUpdateManagementDTO.getProductId())) {
            productReviews.setProductId(productReviewUpdateManagementDTO.getProductId());
        }
        if (productReviewUpdateManagementDTO.getComment() != null && !Objects.equals(productReviews.getComment(), productReviewUpdateManagementDTO.getComment())) {
            productReviews.setComment(productReviewUpdateManagementDTO.getComment());
        }
        if (productReviewUpdateManagementDTO.getRate() != null && !Objects.equals(productReviews.getRate(), productReviewUpdateManagementDTO.getRate())) {
            productReviews.setRate(productReviewUpdateManagementDTO.getRate());
        }
        productReviewRepository.save(productReviews);
        return Const.MESSAGE_CODE.SUCCESS;
    }

    @Override
    public String deletePR(Long id) {
        ProductReviews productReviews = productReviewRepository.findProductReviewsById(id);
        Assert.isTrue(productReviews != null, Const.PRODUCT_REVIEW.PR_NOT_FOUND);
        productReviews.setDeleted(true);
        productReviewRepository.save(productReviews);
        return null;
    }

    @Override
    public ProductReviewDetail getDetail(Long id) {
        ProductReviewDetail productReviewDetail = productReviewRepository.getDetail(id);
        return productReviewDetail;
    }
}
