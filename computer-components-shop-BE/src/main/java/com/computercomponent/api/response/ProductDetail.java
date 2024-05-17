package com.computercomponent.api.response;

import com.computercomponent.api.common.ProductsStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetail {
    private Long id;
    private String name;
    private ProductsStatus status;
    private String brandName;
    private String categoryName;
    private List<ProductSpecificationDetail> details = new ArrayList<>();

    public ProductDetail(Long id, String name, ProductsStatus status, String brandName, String categoryName) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.brandName = brandName;
        this.categoryName = categoryName;
    }
}
