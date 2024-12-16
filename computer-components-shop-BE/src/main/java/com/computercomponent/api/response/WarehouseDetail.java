package com.computercomponent.api.response;

import com.computercomponent.api.common.*;
import com.computercomponent.api.dto.WarehouseProductDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class WarehouseDetail {
    private Long id;
    private String code;
    private String name;
    private String supplier;
    private String description;
    private WarehouseStatus status;
    private TransactionType type;
    private LocalDateTime transactionDate;
    private Integer totalQuantity;
    private BigDecimal totalPrice;
    private Long employeeId;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private List<WarehouseProductDTO> warehouseProductDTOS;

    public WarehouseDetail(Long id, String code, String name, String supplier, String description, WarehouseStatus status,
                           TransactionType type, LocalDateTime transactionDate,
                           Integer totalQuantity, BigDecimal totalPrice, Long employeeId, PaymentMethod paymentMethod, PaymentStatus paymentStatus) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.supplier = supplier;
        this.description = description;
        this.status = status;
        this.type = type;
        this.transactionDate = transactionDate;
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
        this.employeeId = employeeId;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.warehouseProductDTOS = new ArrayList<>();
    }
}
