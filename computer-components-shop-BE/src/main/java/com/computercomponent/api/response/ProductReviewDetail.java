package com.computercomponent.api.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductReviewDetail {
    private Long id;
    private Long productId;
    private Long userId;
    private String comment;
    private Integer rate;

    public ProductReviewDetail(Long id, Long productId, Long userId, String comment, Integer rate) {
        this.id = id;
        this.productId = productId;
        this.userId = userId;
        this.comment = comment;
        this.rate = rate;
    }
}
