package com.computercomponent.api.dto;

import com.computercomponent.api.common.TransactionType;
import com.computercomponent.api.common.WarehouseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseDTO {
    private String code;
    private String name;
    private String supplier;
    private String description;
    private Integer status;
    private Long productId;
    private Integer type;
    private Integer totalQuantity;
    private BigDecimal totalPrice;
}
