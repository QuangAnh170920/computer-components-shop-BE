package com.computercomponent.api.response;

import com.computercomponent.api.common.ProductsStatus;
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
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantityAvailable;
    private ProductsStatus status;
    private String categoryName;
    private BigDecimal finalTotalPrice;

    public ProductDetail(Long id, String name, String description, BigDecimal price, Integer quantityAvailable,
                         ProductsStatus status, String categoryName, BigDecimal finalTotalPrice) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantityAvailable = quantityAvailable;
        this.status = status;
        this.categoryName = categoryName;
        this.finalTotalPrice = finalTotalPrice;
    }
}
