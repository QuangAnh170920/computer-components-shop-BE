package com.computercomponent.api.dto;

import com.computercomponent.api.common.CategoriesStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriesManagementDTO {
    private Long id;
    private String code;
    private String name;
    private String description;
    private CategoriesStatus status;
    private Long parentId;
    private List<CategoriesChildrenDTO> children = new ArrayList<>();

    // Thêm constructor đúng như câu query đang yêu cầu
    public CategoriesManagementDTO(Long id, String code, String name, String description, CategoriesStatus status, Long parentId) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.status = status;
        this.parentId = parentId;
    }
}
