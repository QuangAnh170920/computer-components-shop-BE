package com.computercomponent.api.repository;

import com.computercomponent.api.dto.ProductsManagementDTO;
import com.computercomponent.api.dto.WarehouseManagementDTO;
import com.computercomponent.api.entity.Warehouse;
import com.computercomponent.api.response.ProductDetail;
import com.computercomponent.api.response.WarehouseDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    @Query(value = "select wh from Warehouse wh  where wh.name = :name and wh.deleted = false")
    Warehouse findWarehouseByName(String name);

    @Query(value = "select wh from Warehouse wh  where wh.id = :id and wh.deleted = false")
    Warehouse findWarehouseById(Long id);

    @Query(value = "select new com.computercomponent.api.dto.WarehouseManagementDTO(wh.id, wh.code, wh.name, wh.supplier, wh.description, wh.status, p.code, p.name, wh.type, wh.transactionDate, wh.totalQuantity, wh.totalPrice, wh.employeeId, wh.paymentMethod, wh.paymentStatus) " +
            "from Warehouse as wh " +
            "left join Products p on wh.productId = p.id " +
            "where (wh.code like concat('%', :searchField, '%') " +
            "or wh.name like concat('%', :searchField, '%') " +
            "or wh.supplier like concat('%', :searchField, '%') " +
            "or p.code like concat('%', :searchField, '%') " +
            "or p.name like concat('%', :searchField, '%')) " +
            "and (:status is null or wh.status = :status) " +
            "and (:type is null or wh.type = :type)",
            countQuery = "select count(wh) from Warehouse as wh " +
                    "left join Products p on wh.productId = p.id " +
                    "where (wh.code like concat('%', :searchField, '%') " +
                    "or wh.name like concat('%', :searchField, '%') " +
                    "or wh.supplier like concat('%', :searchField, '%') " +
                    "or p.code like concat('%', :searchField, '%') " +
                    "or p.name like concat('%', :searchField, '%')) " +
                    "and (:status is null or wh.status = :status) " +
                    "and (:type is null or wh.type = :type)")
    Page<WarehouseManagementDTO> findAllAndSearch(String searchField, Integer status, Integer type, Pageable pageable);

    @Query(value = "select new com.computercomponent.api.response.WarehouseDetail(wh.id, wh.code, wh.name, wh.supplier, wh.description, wh.status, p.code, p.name, wh.type, wh.transactionDate, wh.totalQuantity, wh.totalPrice, wh.employeeId, wh.paymentMethod, wh.paymentStatus) " +
            "from Warehouse as wh " +
            "left join Products p on wh.productId = p.id " +
            "where wh.id = :id ",
            countQuery = "select count(wh) from Warehouse as wh " +
                    "left join Products p on wh.productId = p.id " +
                    "where wh.id = :id ")
    WarehouseDetail getDetail(Long id);
}
