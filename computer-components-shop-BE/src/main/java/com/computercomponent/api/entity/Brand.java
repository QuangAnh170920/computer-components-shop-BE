package com.computercomponent.api.entity;

import com.computercomponent.api.common.BrandStatus;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "brand")
public class Brand extends BaseEntity{
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private BrandStatus status;
}
