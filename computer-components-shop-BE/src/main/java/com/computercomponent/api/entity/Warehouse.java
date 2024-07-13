package com.computercomponent.api.entity;

import com.computercomponent.api.common.TransactionType;
import com.computercomponent.api.common.WarehouseStatus;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "warehouse")
//Bảng lưu trữ thông tin về đơn hàng
public class Warehouse extends BaseEntity{
    private String code;
    private String name;
    private String supplier;
    private String description;
    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private WarehouseStatus status;
    private Long productId;
    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TransactionType type; //loại giao dịch (nhập/ xuất)
    private Date transactionDate; //ngày nhập/xuất
    private Integer totalQuantity; //tổng số lượng trong giao dịch
    private BigDecimal totalPrice; //tổng số tiền trong giao dịch
}
