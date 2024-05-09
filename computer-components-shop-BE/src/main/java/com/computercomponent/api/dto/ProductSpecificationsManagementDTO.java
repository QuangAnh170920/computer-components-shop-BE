package com.computercomponent.api.dto;

import com.computercomponent.api.common.ProductSpecificationsStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSpecificationsManagementDTO {
    private Long id;
    private String name;
    private String description;
    private String productName;
    private ProductSpecificationsStatus status;
    private Integer priority;
}
