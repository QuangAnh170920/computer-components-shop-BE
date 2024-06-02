package com.computercomponent.api.entity;

import com.computercomponent.api.common.InventoryStatus;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "inventory")
//Bảng lưu trữ thông tin về hàng tồn kho
public class Inventory extends BaseEntity{
    private Integer quantity;
    private Long productId;
    private Long warehouseId;
    private InventoryStatus status;
}
