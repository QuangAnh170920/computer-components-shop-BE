package com.computercomponent.api.response;

import com.computercomponent.api.common.PromotionStatus;
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
    private Long productId;
    private Integer totalQuantity;
    private BigDecimal totalPrice;
    private BigDecimal importPrice;
    private Date transactionDate;
    private String description;
    private WarehouseStatus status;

    public WarehouseDetail(Long id, String code, String name, String supplier, Long productId, Integer totalQuantity, BigDecimal totalPrice, BigDecimal importPrice, Date transactionDate, String description, WarehouseStatus status) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.supplier = supplier;
        this.productId = productId;
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
        this.importPrice = importPrice;
        this.transactionDate = transactionDate;
        this.description = description;
        this.status = status;
    }
}
