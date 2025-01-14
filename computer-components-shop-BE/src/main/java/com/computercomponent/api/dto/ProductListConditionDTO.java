package com.computercomponent.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ProductListConditionDTO {
    private Long id;
    private String name;
    private BigDecimal price;
    private BigDecimal finalTotalPrice;
    private Integer quantityAvailable;
    private String imageUrl;
    private Integer rate;
    private Long categoryId;
}
