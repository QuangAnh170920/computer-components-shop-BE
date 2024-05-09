package com.computercomponent.api.request;

import lombok.Data;

@Data
public class ProductSpecificationsRequest extends BasePageRequest {
    private String searchField;
    private Integer status;
}
