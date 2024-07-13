package com.computercomponent.api.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "product_image")
public class ProductImage extends BaseEntity {
    private String imageUrl;
    private Long productId;
}
