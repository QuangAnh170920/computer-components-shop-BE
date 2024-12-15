package com.computercomponent.api.dto;

import com.computercomponent.api.common.ProductsStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateRequestDTO {
    private Long id;
    private String code;
    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal finalTotalPrice;
    private String power;
    private String imageUrl;
    private Long categoryId;
    private Long promotionId;
    private List<ProductFeaturesDTO> productFeatures;
}
