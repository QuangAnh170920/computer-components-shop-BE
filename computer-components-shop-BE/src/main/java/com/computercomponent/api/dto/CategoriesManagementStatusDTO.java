package com.computercomponent.api.dto;

import com.computercomponent.api.common.CategoriesStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriesManagementStatusDTO {
    private Long id;
    private CategoriesStatus status;
}
