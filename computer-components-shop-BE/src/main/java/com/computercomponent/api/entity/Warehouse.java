package com.computercomponent.api.entity;

import com.computercomponent.api.common.WarehouseStatus;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "warehouse")
//Bảng lưu trữ thông tin về các kho hàng
public class Warehouse extends BaseEntity{
    private String code;
    private String name;
    private String location;
    private String description;
    private WarehouseStatus status;
}
