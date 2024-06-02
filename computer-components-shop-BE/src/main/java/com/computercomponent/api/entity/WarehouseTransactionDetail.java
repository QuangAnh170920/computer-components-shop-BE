package com.computercomponent.api.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "warehouse_transaction_detail")
public class WarehouseTransactionDetail extends BaseEntity{
    private Long transactionId;
    private Long productId;
    private Long quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
}
