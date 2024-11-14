package com.computercomponent.api.dto;

import com.computercomponent.api.common.PaymentMethod;
import com.computercomponent.api.common.PaymentStatus;
import com.computercomponent.api.common.TransactionType;
import com.computercomponent.api.common.WarehouseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseDTO {
    private String code;
    private String name;
    private String supplier;
    private String description;
    private WarehouseStatus status; // Thay Integer bằng WarehouseStatus
    private TransactionType type; // Thay Integer bằng TransactionType
    private Integer totalQuantity;
    private BigDecimal totalPrice;
    private Long employeeId; // ID của nhân viên thực hiện
    private PaymentMethod paymentMethod; // Phương thức thanh toán
    private PaymentStatus paymentStatus; // Trạng thái thanh toán
    private List<WarehouseProductDTO> warehouseProductDTOS;
}
