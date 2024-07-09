package com.computercomponent.api.entity;

import com.computercomponent.api.common.PromotionStatus;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "promotion")
public class Promotion extends BaseEntity{
    @Column(name = "code")
    private String code;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PromotionStatus status;
    @Column(name = "price")
    private BigDecimal price;
}
