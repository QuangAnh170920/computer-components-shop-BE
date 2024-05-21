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
    private Integer totalQuantity; // tổng số lượng sản phẩm trong đơn hàng
    private BigDecimal totalPrice; // tổng tiền của cả đơn hàng
    private BigDecimal discountAmount; // số tiền được chiết khấu
    private BigDecimal finalTotalPrice; // tổng số tiền sau khi áp dụng chiết khấu
    private Integer discountPercentage; // Số phần trăm chiết khấu được áp dụng cho đơn hàng
}
