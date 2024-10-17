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
    private BigDecimal finalTotalPrice; // tổng số tiền sau khi áp dụng chiết khấu
    private String power;
    private Integer quantityAvailable; // số lượng có sẵn
    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProductsStatus status;
    private Long categoryId;
}
