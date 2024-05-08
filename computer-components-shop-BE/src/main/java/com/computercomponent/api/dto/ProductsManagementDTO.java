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
    private String name;
    private String description;
    private String brandName;
    private String categoryName;
    private BigDecimal price;
    private ProductsStatus status;
}
