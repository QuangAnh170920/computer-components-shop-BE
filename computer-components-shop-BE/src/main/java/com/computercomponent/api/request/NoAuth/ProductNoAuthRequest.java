package com.computercomponent.api.request.NoAuth;

import com.computercomponent.api.request.BasePageRequest;
import lombok.Data;

@Data
public class ProductNoAuthRequest extends BasePageRequest {
    private Long categoryId;
}
