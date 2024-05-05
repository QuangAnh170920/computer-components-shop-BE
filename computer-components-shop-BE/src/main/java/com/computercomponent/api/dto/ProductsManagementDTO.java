package com.computercomponent.api.dto;

import com.computercomponent.api.common.CategoriesStatus;
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
    private Long brandId;
    private Long categoryId;
    private BigDecimal price;
    private CategoriesStatus status;
}
