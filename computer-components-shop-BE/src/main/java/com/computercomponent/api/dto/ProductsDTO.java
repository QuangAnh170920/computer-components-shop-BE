package com.computercomponent.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductsDTO {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantityAvailable;
    private Integer status;
    private Integer discountPercentage;
}
