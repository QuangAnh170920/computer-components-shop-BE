package com.computercomponent.api.entity;

import com.computercomponent.api.common.PaymentMethod;
import com.computercomponent.api.common.PaymentStatus;
import com.computercomponent.api.common.TransactionType;
import com.computercomponent.api.common.WarehouseStatus;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TransactionType type; //loại giao dịch (nhập/ xuất)
    private LocalDateTime transactionDate; //ngày nhập/xuất
    private Integer totalQuantity; //tổng số lượng trong giao dịch
    private BigDecimal totalPrice; //tổng số tiền trong giao dịch
    private Long employeeId; //id của nhân viên thực hiện
    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod; // phương thức thanh toán
    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;
}
