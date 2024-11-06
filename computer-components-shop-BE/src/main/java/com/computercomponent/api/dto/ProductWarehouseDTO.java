package com.computercomponent.api.dto;

import com.computercomponent.api.common.ProductsStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductWarehouseDTO {
    private Long id;
    private String code;               // Mã sản phẩm
    private String name;               // Tên sản phẩm
    private Integer quantityAvailable;  // Số lượng có sẵn
    private ProductsStatus status;      // Trạng thái sản phẩm
}
