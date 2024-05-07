package com.computercomponent.api.entity;

import com.computercomponent.api.common.ProductSpecificationsStatus;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "product_specifications")
public class ProductSpecifications extends BaseEntity{
    private Long productId;
    private String name;
    private String value;
    private String description;
    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProductSpecificationsStatus status;
    private Integer priority;
}
