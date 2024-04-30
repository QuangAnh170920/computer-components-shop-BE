package com.computercomponent.api.dto;

import com.computercomponent.api.common.BrandStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrandDTO {
    private String name;
    private String description;
    private BrandStatus status;
}
