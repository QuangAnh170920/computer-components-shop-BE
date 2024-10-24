package com.computercomponent.api.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "orders_detail")
public class OrderDetail extends BaseEntity {
    @Column(name = "order_id")
    private Long orderId; // Trường chứa ID của đơn hàng

    private Long productId;
    private Integer quantity; // Số lượng của từng sản phẩm trong đơn hàng
}
