package com.computercomponent.api.dto;

import com.computercomponent.api.common.OrderStatus;
import com.computercomponent.api.common.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdersManagementDTO {
    private Long id;
    private String name;
    private String description;
    private String shippingAddress;
    private PaymentMethod paymentMethod;
    private Integer totalQuantity;
    private BigDecimal totalPrice;
    private OrderStatus status;
    private String userName;
}
