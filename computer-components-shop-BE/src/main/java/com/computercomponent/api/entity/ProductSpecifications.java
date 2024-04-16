package com.computercomponent.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "product_specifications", schema = "qap_store", catalog = "")
@Data
public class ProductSpecifications {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "specification_id")
    private int specificationId;
    @Basic
    @Column(name = "product_id")
    private Integer productId;
    @Basic
    @Column(name = "specification_name")
    private String specificationName;
    @Basic
    @Column(name = "specification_value")
    private String specificationValue;
    @Basic
    @Column(name = "create_at")
    private Date createAt;
    @Basic
    @Column(name = "update_at")
    private Date updateAt;
    @Basic
    @Column(name = "create_by")
    private String createBy;
    @Basic
    @Column(name = "update_by")
    private String updateBy;
    @Basic
    @Column(name = "deleted")
    private Integer deleted;
}
