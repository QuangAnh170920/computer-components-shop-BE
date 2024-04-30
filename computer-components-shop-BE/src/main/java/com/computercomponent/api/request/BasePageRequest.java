package com.computercomponent.api.request;

import lombok.Data;

@Data
public class BasePageRequest {
    private int pageNumber = 1;

    private int pageSize = 20;
}
