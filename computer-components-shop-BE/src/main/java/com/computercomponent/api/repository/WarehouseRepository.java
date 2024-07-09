package com.computercomponent.api.repository;

import com.computercomponent.api.entity.Promotion;
import com.computercomponent.api.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
}
