package com.computercomponent.api.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "orders_detail")
public class OrderDetail extends BaseEntity {
    private Long orderId;
    private Long productId;
    private Integer quantity; // số lượng của từng sản phẩm trong đơn hàng
}
