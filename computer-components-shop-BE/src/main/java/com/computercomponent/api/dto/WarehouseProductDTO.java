package com.computercomponent.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseProductDTO {
    private Long id;
    private Long warehouseId;
    private Long productId;
    private Integer quantity;  // Số lượng của từng sản phẩm có trong đơn
    private BigDecimal price; // Số tiền của từng sản phẩm có trong đơn
}
