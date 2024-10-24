package com.computercomponent.api.response;

import com.computercomponent.api.common.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
public class WarehouseDetail {
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

    public WarehouseDetail(Long id, String code, String name, String supplier, String description, WarehouseStatus status,
                           String productCode, String productName, TransactionType type, LocalDateTime transactionDate,
                           Integer totalQuantity, BigDecimal totalPrice, Long employeeId, PaymentMethod paymentMethod, PaymentStatus paymentStatus) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.supplier = supplier;
        this.description = description;
        this.status = status;
        this.productCode = productCode;
        this.productName = productName;
        this.type = type;
        this.transactionDate = transactionDate;
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
        this.employeeId = employeeId;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
    }
}
