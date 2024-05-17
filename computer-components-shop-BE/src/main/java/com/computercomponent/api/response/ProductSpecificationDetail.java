package com.computercomponent.api.response;

import com.computercomponent.api.common.ProductSpecificationsStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSpecificationDetail {
    private Long id;
    private String name;
    private String value;
    private Integer priority;
    private ProductSpecificationsStatus status;
}
