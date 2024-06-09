package com.computercomponent.api.response;

import com.computercomponent.api.common.CategoriesStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoriesDetail {
    private Long id;
    private String code;
    private String name;
    private String description;
    private CategoriesStatus status;

    public CategoriesDetail(Long id, String code, String name, String description, CategoriesStatus status) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.status = status;
    }
}
