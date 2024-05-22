package com.computercomponent.api.entity;

import com.computercomponent.api.common.BrandStatus;
import com.computercomponent.api.common.OrderStatus;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
@Entity
@Data
@Table(name = "orders")
public class Orders extends BaseEntity{
    private String name;
    private String description;
    private Long userId;
    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;
    private String shippingAddress; // địa chỉ giao hàng
    private String paymentMethod; // phương thức thanh toán
    private Integer totalQuantity; // tổng số lượng sản phẩm trong đơn hàng
    private BigDecimal totalPrice; // tổng tiền của cả đơn hàng
}
