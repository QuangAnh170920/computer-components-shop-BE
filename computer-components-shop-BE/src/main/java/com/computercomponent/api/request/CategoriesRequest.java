package com.computercomponent.api.request;

import lombok.Data;

@Data
public class CategoriesRequest extends BasePageRequest {
    private String searchField;
    private Integer status;
    private Long parentId;
}
