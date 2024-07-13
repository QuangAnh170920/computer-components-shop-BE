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
public class WarehouseUpdateRequestDTO {
    private Long id;
    private String code;
    private String name;
    private String supplier;
    private String description;
    private WarehouseStatus status;
    private Long productId;
    private TransactionType type;
    private Integer totalQuantity; //tổng số lượng trong giao dịch
    private BigDecimal totalPrice; //tổng số tiền trong giao dịch
}
