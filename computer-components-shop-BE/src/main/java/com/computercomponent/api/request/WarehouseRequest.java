package com.computercomponent.api.request;

import lombok.Data;

@Data
public class WarehouseRequest extends BasePageRequest {
    private String searchField;
    private Integer status;
    private Integer type;
}
