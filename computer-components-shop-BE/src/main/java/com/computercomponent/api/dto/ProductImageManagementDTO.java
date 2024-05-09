package com.computercomponent.api.dto;

import com.computercomponent.api.common.ProductImageStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductImageManagementDTO {
    private Long id;
    private String imageUrl;
    private String description;
    private String productName;
    private ProductImageStatus status;
}
