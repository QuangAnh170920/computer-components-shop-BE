package com.computercomponent.api.dto;

import com.computercomponent.api.common.ProductSpecificationsStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSpecificationsDTO {
    private String name;
    private String description;
    private Long productId;
    private ProductSpecificationsStatus status;
    private String value;
    private Integer priority;
}
