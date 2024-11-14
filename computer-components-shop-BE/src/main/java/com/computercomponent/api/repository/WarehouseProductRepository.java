package com.computercomponent.api.repository;

import com.computercomponent.api.entity.Warehouse;
import com.computercomponent.api.entity.WarehouseProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseProductRepository extends JpaRepository<WarehouseProduct, Long> {
}
