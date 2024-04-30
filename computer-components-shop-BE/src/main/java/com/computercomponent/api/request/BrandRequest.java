package com.computercomponent.api.request;

import lombok.Data;

@Data
public class BrandRequest extends BasePageRequest {
    private String name;
}
