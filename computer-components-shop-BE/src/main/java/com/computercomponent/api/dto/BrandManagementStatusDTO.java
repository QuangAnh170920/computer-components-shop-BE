package com.computercomponent.api.dto;

import com.computercomponent.api.common.BrandStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrandManagementStatusDTO {
    private Long id;
    private BrandStatus status;
}
