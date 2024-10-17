package com.computercomponent.api.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "product_features")
public class ProductFeatures extends BaseEntity{
    private Long productId;
    private String feature;
    private Integer priority;
}
