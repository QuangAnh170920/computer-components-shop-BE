package com.computercomponent.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductReviewManagementDTO {
    private Long id;
    private String productName;
    private String userName;
    private String comment;
    private Integer rate;
}
