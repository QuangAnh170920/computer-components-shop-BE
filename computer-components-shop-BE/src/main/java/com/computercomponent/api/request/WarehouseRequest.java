package com.computercomponent.api.request;

import com.computercomponent.api.common.TransactionType;
import lombok.Data;

@Data
public class WarehouseRequest extends BasePageRequest {
    private String searchField;
    private Integer status;
    private TransactionType type;
}
