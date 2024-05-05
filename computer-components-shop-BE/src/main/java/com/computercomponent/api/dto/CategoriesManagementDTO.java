package com.computercomponent.api.dto;

import com.computercomponent.api.common.BrandStatus;
import com.computercomponent.api.common.CategoriesStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriesManagementDTO {
    private Long id;
    private String name;
    private String description;
    private CategoriesStatus status;
}
