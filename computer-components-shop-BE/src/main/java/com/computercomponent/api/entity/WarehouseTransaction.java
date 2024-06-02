package com.computercomponent.api.entity;

import com.computercomponent.api.common.TransactionType;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "warehouse_transaction")
public class WarehouseTransaction extends BaseEntity{
    private TransactionType type; //loại giao dịch (nhập/ xuất)
    private Long warehouseId;
    private Date transactionDate;
    private Integer totalQuantity; //tổng số lượng trong giao dịch
    private BigDecimal totalPrice; //tổng số tiền trong giao dịch
    private String description;
}
