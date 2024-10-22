package com.computercomponent.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductsDTO {
    private String code;
    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal finalTotalPrice;
    private Integer quantityAvailable;
    private String power;
    private Integer status;
    private Long imageUrl;
    private List<ProductFeaturesDTO> productFeatures;
}
