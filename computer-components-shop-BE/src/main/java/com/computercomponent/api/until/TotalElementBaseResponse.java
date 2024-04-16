package com.computercomponent.until;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class TotalElementBaseResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer totalPage;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long totalElement;
}