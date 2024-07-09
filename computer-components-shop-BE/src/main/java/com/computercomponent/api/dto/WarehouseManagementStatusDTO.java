package com.computercomponent.api.dto;

import com.computercomponent.api.common.WarehouseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseManagementStatusDTO {
    private Long id;
    private WarehouseStatus status;
}
