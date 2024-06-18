package com.computercomponent.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductReviewDTO {
    private Long productId;
    private Long userId;
    private String comment;
    private Integer rate;
}
