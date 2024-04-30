package com.computercomponent.api.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "product_reviews")
public class ProductReviews extends BaseEntity{
    private Long userId;
    private Long productId;
    private Integer rate;
    private String comment;
}
