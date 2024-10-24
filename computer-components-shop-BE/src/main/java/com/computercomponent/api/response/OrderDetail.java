package com.computercomponent.api.response;

import com.computercomponent.api.common.OrderStatus;
import com.computercomponent.api.common.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {
    private Long id;
    private String name;
    private String description;
    private String shippingAddress;
    private PaymentMethod paymentMethod;
    private Integer totalQuantity;
    private BigDecimal totalPrice;
    private OrderStatus status;
    private String userFullName;
    private List<OrderDetailResponse> details = new ArrayList<>();

    public OrderDetail (
            Long id,
            String name,
            String description,
            String shippingAddress,
            PaymentMethod paymentMethod,
            Integer totalQuantity,
            BigDecimal totalPrice,
            OrderStatus status,
            String userFullName
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.shippingAddress = shippingAddress;
        this.paymentMethod = paymentMethod;
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
        this.status = status;
        this.userFullName = userFullName;
    }
}
