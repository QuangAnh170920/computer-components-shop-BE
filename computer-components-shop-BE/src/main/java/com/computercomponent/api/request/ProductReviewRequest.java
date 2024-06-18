package com.computercomponent.api.request;

import lombok.Data;

@Data
public class ProductReviewRequest extends BasePageRequest {
    private Long productId;
    private Long userId;
}
