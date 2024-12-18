package com.computercomponent.api.dto;

import com.computercomponent.api.common.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDTO {
    private String code;
    private String name;
    private String shippingAddress;
    private String paymentMethod;
    private Integer totalQuantity;
    private BigDecimal totalPrice;
    private OrderStatus status;
    private Long userId;
    private LocalDateTime createdAt;
    private List<OrderDetailDTO> orderDetail;
    private String description;
}
