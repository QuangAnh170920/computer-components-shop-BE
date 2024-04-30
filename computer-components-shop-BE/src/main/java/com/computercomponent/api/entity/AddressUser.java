package com.computercomponent.api.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "address_user")
public class AddressUser extends BaseEntity{
    private Long userId;
    private String add1;
    private String add2;
    private String city;
    private String state;
    private String country;
}
