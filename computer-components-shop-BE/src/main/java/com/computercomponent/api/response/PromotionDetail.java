package com.computercomponent.api.response;

import com.computercomponent.api.common.PromotionStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PromotionDetail {
    private Long id;
    private String code;
    private String name;
    private String description;
    private PromotionStatus status;

    public PromotionDetail(Long id, String code, String name, String description, PromotionStatus status) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.status = status;
    }
}
