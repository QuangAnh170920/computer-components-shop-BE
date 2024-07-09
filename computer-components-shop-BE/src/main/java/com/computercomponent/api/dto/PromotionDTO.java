package com.computercomponent.api.dto;

import com.computercomponent.api.common.PromotionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromotionDTO {
    private String code;
    private String name;
    private BigDecimal price;
    private String description;
    private PromotionStatus status;
}
