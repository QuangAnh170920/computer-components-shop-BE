package com.computercomponent.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@Table(name = "product_images", schema = "qap_store", catalog = "")
public class ProductImages {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "image_id")
    private int imageId;
    @Basic
    @Column(name = "product_id")
    private Integer productId;
    @Basic
    @Column(name = "image_url")
    private String imageUrl;
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
