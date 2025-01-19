package com.computercomponent.api.response.NoAuth;

import com.computercomponent.api.common.ProductsStatus;
import com.computercomponent.api.dto.ProductFeaturesDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ProductDetailNoAuth {
    private Long id;
    private String imageUrl;
    private String code;
    private String name;
    private BigDecimal price;
    private BigDecimal finalTotalPrice;
    private Integer quantityAvailable;
    private List<ProductFeaturesDTO> productFeatures;

    public ProductDetailNoAuth(Long id,String imageUrl, String code, String name, BigDecimal price, BigDecimal finalTotalPrice, Integer quantityAvailable) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.code = code;
        this.name = name;
        this.price = price;
        this.finalTotalPrice = finalTotalPrice;
        this.quantityAvailable = quantityAvailable;
        this.productFeatures = new ArrayList<>();
    }
}
