package com.computercomponent.api.request;

import com.computercomponent.api.common.ProductsStatus;
import lombok.Data;

@Data
public class ProductsRequest extends BasePageRequest {
    private String searchField;
    private Integer status;
}
