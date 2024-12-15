package com.computercomponent.api.response;

import com.computercomponent.api.common.ProductsStatus;
import com.computercomponent.api.dto.ProductFeaturesDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ProductDetail {
    private Long id;
    private String code;
    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal finalTotalPrice;
    private String power;
    private ProductsStatus status;
    private String imageUrl;
    private Long promotionId;
    private Long categoryId;
    private List<ProductFeaturesDTO> productFeatures;

    public ProductDetail(Long id, String code, String name, String description, BigDecimal price, BigDecimal finalTotalPrice,
                         String power, ProductsStatus status,
                         String imageUrl, Long promotionId, Long categoryId) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.price = price;
        this.finalTotalPrice = finalTotalPrice;
        this.power = power;
        this.status = status;
        this.imageUrl = imageUrl;
        this.promotionId = promotionId;
        this.categoryId = categoryId;
        this.productFeatures = new ArrayList<>();
    }

}
