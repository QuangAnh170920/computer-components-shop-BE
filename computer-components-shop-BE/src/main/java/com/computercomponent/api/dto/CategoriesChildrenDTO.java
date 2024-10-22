package com.computercomponent.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriesChildrenDTO {
    private Long id;
    private String code;
    private String name;
    private Long parentId;
    private List<CategoriesChildrenDTO> children;
}
