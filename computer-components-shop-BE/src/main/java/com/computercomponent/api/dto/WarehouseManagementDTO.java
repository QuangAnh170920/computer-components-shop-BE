package com.computercomponent.api.dto;

import com.computercomponent.api.common.PaymentMethod;
import com.computercomponent.api.common.PaymentStatus;
import com.computercomponent.api.common.TransactionType;
import com.computercomponent.api.common.WarehouseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseManagementDTO {
    private Long id;
    private String code;
    private String name;
    private String supplier;
    private String description;
    private WarehouseStatus status;
    private String productCode;
    private String productName;
    private TransactionType type;
    private LocalDateTime transactionDate;
    private Integer totalQuantity;
    private BigDecimal totalPrice;
    private Long employeeId;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
}
