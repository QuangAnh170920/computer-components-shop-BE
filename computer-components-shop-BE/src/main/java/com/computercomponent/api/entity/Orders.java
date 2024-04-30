package com.computercomponent.api.entity;

import com.computercomponent.api.common.BrandStatus;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
@Entity
@Data
@Table(name = "orders")
public class Orders extends BaseEntity{
    private BigDecimal totalAmount;
    private String description;
    private Long userId;
    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private BrandStatus status;
}
