package com.computercomponent.api.request;

import lombok.Data;

@Data
public class ProductImageRequest extends BasePageRequest {
    private String searchField;
    private Integer status;
}
