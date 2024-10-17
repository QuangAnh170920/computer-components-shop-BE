package com.computercomponent.api.entity;

import com.computercomponent.api.common.CategoriesStatus;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "categories")
public class Categories extends BaseEntity{
    @Column(name = "code")
    private String code;
    private String name;
    private Long parentId;
    private String description;
    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CategoriesStatus status;
}
