package com.computercomponent.api.dto;

import com.computercomponent.api.common.PromotionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromotionManagementStatusDTO {
    private Long id;
    private PromotionStatus status;
}
