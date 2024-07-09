package com.computercomponent.api.dto;

import com.computercomponent.api.common.WarehouseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseManagementDTO {
    private Long id;
    private String name;
    private String supplier;
    private Long productId;
    private Integer totalQuantity;
    private BigDecimal totalPrice;
    private BigDecimal importPrice;
    private Date transactionDate;
    private String description;
    private WarehouseStatus status;
}
