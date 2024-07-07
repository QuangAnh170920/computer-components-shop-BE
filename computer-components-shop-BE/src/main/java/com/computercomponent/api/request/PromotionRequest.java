package com.computercomponent.api.request;

import lombok.Data;

@Data
public class PromotionRequest extends BasePageRequest {
    private String searchField;
    private Integer status;
}
