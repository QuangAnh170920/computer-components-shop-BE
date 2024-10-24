package com.computercomponent.api.entity;

import com.computercomponent.api.common.OrderStatus;
import com.computercomponent.api.common.PaymentMethod;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Orders extends BaseEntity{
    private String code;
    private String name;
    private String description;
    private Long userId;
    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;
    private String shippingAddress; // địa chỉ giao hàng
    private PaymentMethod paymentMethod; // phương thức thanh toán
    private Integer totalQuantity; // tổng số lượng sản phẩm trong đơn hàng
    private BigDecimal totalPrice; // tổng tiền của cả đơn hàng
}
