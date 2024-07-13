package com.computercomponent.api.response;

import com.computercomponent.api.common.PromotionStatus;
import com.computercomponent.api.common.TransactionType;
import com.computercomponent.api.common.WarehouseStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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
    private Date transactionDate;
    private Integer totalQuantity;
    private BigDecimal totalPrice;

    public WarehouseDetail(Long id, String code, String name, String supplier, String description, WarehouseStatus status, String productCode, String productName, TransactionType type, Date transactionDate, Integer totalQuantity, BigDecimal totalPrice) {
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
    }
}
