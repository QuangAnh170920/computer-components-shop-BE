package com.computercomponent.api.response;

import com.computercomponent.api.common.BrandStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BrandDetail {
    private Long id;
    private String code;
    private String name;
    private String description;
    private BrandStatus status;

    public BrandDetail(Long id, String code, String name, String description, BrandStatus status) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.status = status;
    }
}
