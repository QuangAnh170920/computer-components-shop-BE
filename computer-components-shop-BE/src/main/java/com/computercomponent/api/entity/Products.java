package com.computercomponent.api.entity;

import com.computercomponent.api.common.ProductsStatus;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "products")
public class Products extends BaseEntity{
    private String code;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantityAvailable;
    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProductsStatus status;
    private Long categoryId;
    private Long brandId;
    private BigDecimal discountAmount; // số tiền được chiết khấu
    private Integer discountPercentage; // Số phần trăm chiết khấu được áp dụng cho sản phẩm
    private BigDecimal finalTotalPrice; // tổng số tiền sau khi áp dụng chiết khấu
}
