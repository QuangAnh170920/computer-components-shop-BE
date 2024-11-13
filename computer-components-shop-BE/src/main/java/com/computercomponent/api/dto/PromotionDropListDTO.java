package com.computercomponent.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PromotionDropListDTO {
    private Long id;
    private String name;
    private Integer discountPercentage;
}
