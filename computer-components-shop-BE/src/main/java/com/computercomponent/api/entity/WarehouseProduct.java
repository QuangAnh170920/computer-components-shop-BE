package com.computercomponent.api.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "warehouse_product")
public class WarehouseProduct extends BaseEntity{
    private Long warehouseId;
    private Long productId;
    private Integer quantity;  // Số lượng của từng sản phẩm có trong đơn
    private BigDecimal price; // Số tiền của từng sản phẩm có trong đơn
}
