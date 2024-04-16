package com.computercomponent.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Data
@Table(name = "order_details", schema = "qap_store", catalog = "")
public class OrderDetails {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "order_detail_id")
    private int orderDetailId;
    @Basic
    @Column(name = "order_id")
    private Integer orderId;
    @Basic
    @Column(name = "product_id")
    private Integer productId;
    @Basic
    @Column(name = "quantity")
    private int quantity;
    @Basic
    @Column(name = "unit_price")
    private BigDecimal unitPrice;
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
