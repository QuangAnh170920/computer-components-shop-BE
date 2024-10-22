package com.computercomponent.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductFeaturesDTO {
    private Long productId;
    private String feature;
    private Integer priority;
}
