package com.computercomponent.api.dto;

import com.computercomponent.api.common.ProductsStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductsManagementDTO {
    private Long id;
    private String code;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantityAvailable;
    private ProductsStatus status;
    private String categoryName;
    private String brandName;
    private BigDecimal discountAmount;
    private Integer discountPercentage;
    private BigDecimal finalTotalPrice;
}
