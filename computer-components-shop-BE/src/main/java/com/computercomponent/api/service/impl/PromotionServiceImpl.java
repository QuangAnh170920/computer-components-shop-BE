package com.computercomponent.api.service.impl;

import com.computercomponent.api.common.Const;
import com.computercomponent.api.common.PromotionStatus;
import com.computercomponent.api.dto.*;
import com.computercomponent.api.entity.Promotion;
import com.computercomponent.api.repository.PromotionRepository;
import com.computercomponent.api.request.PromotionRequest;
import com.computercomponent.api.response.PromotionDetail;
import com.computercomponent.api.service.PromotionService;
import com.computercomponent.api.until.DataUtil;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Objects;

@Service
public class PromotionServiceImpl implements PromotionService {
    @Autowired
    private PromotionRepository promotionRepository;

    @Override
    public String create(PromotionDTO promotionDTO) {
        validatePromotion(promotionDTO);
        Promotion promotion = new Promotion();
        BeanUtils.copyProperties(promotionDTO, promotion);
        promotionRepository.save(promotion);
        return Const.MESSAGE_CODE.SUCCESS;
    }

    @Override
    public Page<PromotionManagementDTO> getList(PromotionRequest promotionRequest) {
        PageRequest pageRequest = DataUtil.getPageable(promotionRequest.getPageNumber(), promotionRequest.getPageSize());
        if (promotionRequest.getSearchField() == null) {
            promotionRequest.setSearchField("");
        }
        if (promotionRequest.getStatus() != null) {
            PromotionStatus promotionStatus = PromotionStatus.fromValue(promotionRequest.getStatus());
            Assert.notNull(promotionStatus, Const.MESSAGE_CODE.STATUS_NOT_FOUND);
        }
        return promotionRepository.findAllAndSearch(promotionRequest.getSearchField().trim(), promotionRequest.getStatus(), pageRequest);
    }

    @Override
    public String update(PromotionManagementDTO promotionManagementDTO) {
        Promotion promotion = promotionRepository.findPromotionById(promotionManagementDTO.getId());
        Assert.isTrue(promotion != null, Const.PROMOTION.PROMOTION_NOT_FOUND);

        if (promotionManagementDTO.getName() != null && !Objects.equals(promotion.getName(), promotionManagementDTO.getName())) {
            validateUpdatePromotionName(promotionManagementDTO.getName());
            promotion.setName(promotionManagementDTO.getName());
        }

        if (promotionManagementDTO.getDescription() != null && !Objects.equals(promotion.getDescription(), promotionManagementDTO.getDescription())) {
            validatePromotionDescription(promotionManagementDTO.getDescription());
            promotion.setDescription(promotionManagementDTO.getDescription());
        }

        if (promotionManagementDTO.getCode() != null && !Objects.equals(promotion.getCode(), promotionManagementDTO.getCode())) {
            validatePromotionCode(promotionManagementDTO.getCode());
            promotion.setCode(promotionManagementDTO.getCode());
        }

        if (promotionManagementDTO.getDiscountPercentage() != null && !Objects.equals(promotion.getDiscountPercentage(), promotionManagementDTO.getDiscountPercentage())) {
            validateDiscountPercentage(promotionManagementDTO.getDiscountPercentage());
            promotion.setDiscountPercentage(promotionManagementDTO.getDiscountPercentage());
        }

        if (promotionManagementDTO.getPrice() != null && !Objects.equals(promotion.getPrice(), promotionManagementDTO.getPrice())) {
            promotion.setPrice(promotionManagementDTO.getPrice());
        }

        promotionRepository.save(promotion);
        return Const.MESSAGE_CODE.SUCCESS;
    }

    @Override
    public String delete(Long id) {
        Promotion promotion = promotionRepository.findPromotionById(id);
        Assert.isTrue(promotion != null, Const.PROMOTION.PROMOTION_NOT_FOUND);
        promotion.setDeleted(true);
        promotionRepository.save(promotion);
        return null;
    }

    @Override
    public PromotionDetail getDetail(Long id) {
        PromotionDetail promotionDetail = promotionRepository.getDetail(id);
        return promotionDetail;
    }

    @Override
    public PromotionManagementStatusDTO updateStatus(PromotionManagementStatusDTO promotionManagementStatusDTO) {
        Promotion promotion = promotionRepository.findPromotionById(promotionManagementStatusDTO.getId());
        Assert.isTrue(promotion != null, Const.PROMOTION.PROMOTION_NOT_FOUND);
        BeanUtils.copyProperties(promotionManagementStatusDTO, promotion);
        promotionRepository.save(promotion);
        return null;
    }

    private void validatePromotion(PromotionDTO promotionDTO) {
        promotionDTO.setName(validatePromotionName(promotionDTO.getName()));
        promotionDTO.setCode(validatePromotionCode(promotionDTO.getCode()));
        if (promotionDTO.getDiscountPercentage() < 0 || promotionDTO.getDiscountPercentage() >= 100) {
            throw new RuntimeException(Const.PROMOTION.DISCOUNT_PERCENTAGE_MUST_BE_BETWEEN_0_AND_100);
        }
        if (!NumberUtils.isCreatable(promotionDTO.getDiscountPercentage().toString())) {
            throw new RuntimeException(Const.PROMOTION.INVALID_PROMOTION_PERCENTAGE);
        }
    }

    private void validateUpdatePromotion(PromotionManagementDTO promotionManagementDTO) {
        promotionManagementDTO.setName(validatePromotionName(promotionManagementDTO.getName()));
    }

    private String validatePromotionName(String str) {
        if (DataUtil.isNullOrEmpty(str)) {
            throw new RuntimeException(Const.PROMOTION.PROMOTION_NAME_IS_NOT_EMPTY);
        } else {
            String name = DataUtil.replaceSpaceSolr(str);
            if(name.length() > 200){
                throw new RuntimeException(Const.PROMOTION.PROMOTION_NAME_MORE_THAN_200_CHAR);
            }
            Promotion promotion = promotionRepository.findPromotionByName(name);
            if (promotion != null) {
                throw new RuntimeException(Const.PROMOTION.PROMOTION_NAME_EXISTED);
            }else {
                return name;
            }
        }
    }

    private String validateUpdatePromotionName(String str) {
        String name = DataUtil.replaceSpaceSolr(str);
        if(name.length() > 200){
            throw new RuntimeException(Const.PROMOTION.PROMOTION_NAME_MORE_THAN_200_CHAR);
        }
        Promotion promotion = promotionRepository.findPromotionByName(name);
        if (promotion != null) {
            throw new RuntimeException(Const.PROMOTION.PROMOTION_NAME_EXISTED);
        }else {
            return name;
        }
    }

    private String validatePromotionCode(String str) {
        if (DataUtil.isNullOrEmpty(str)) {
            throw new RuntimeException(Const.PROMOTION.PROMOTION_NAME_IS_NOT_EMPTY);
        } else {
            String code = DataUtil.replaceSpaceSolr(str);
            if(code.length() > 200){
                throw new RuntimeException(Const.PROMOTION.PROMOTION_CODE_MORE_THAN_200_CHAR);
            }
            Promotion promotion = promotionRepository.findPromotionsByCode(code);
            if (promotion != null) {
                throw new RuntimeException(Const.PROMOTION.PROMOTION_CODE_EXISTED);
            }else {
                return code;
            }
        }
    }

    private void validatePromotionDescription(String description) {
        Assert.isTrue(description == null || description.length() <= 255, Const.PROMOTION.INVALID_DESCRIPTION_LENGTH);
    }

    private void validateDiscountPercentage(Integer discountPercentage) {
        if (discountPercentage < 0 || discountPercentage >= 100) {
            throw new RuntimeException(Const.PROMOTION.DISCOUNT_PERCENTAGE_MUST_BE_BETWEEN_0_AND_100);
        }
    }
}
