package com.computercomponent.api.entity;

import com.computercomponent.api.common.ProductImageStatus;
import com.computercomponent.api.common.ProductsStatus;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "product_image")
public class ProductImage extends BaseEntity{
    private Long productId;
    private String imageUrl;
    private String description;
    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProductImageStatus status;
}
