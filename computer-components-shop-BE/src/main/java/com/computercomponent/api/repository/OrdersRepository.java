package com.computercomponent.api.repository;

import com.computercomponent.api.dto.OrdersManagementDTO;
import com.computercomponent.api.dto.ProductsManagementDTO;
import com.computercomponent.api.entity.Orders;
import com.computercomponent.api.response.OrderDetail;
import com.computercomponent.api.response.ProductDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
    @Query(value = "select o from Orders o  where o.id = :id and o.deleted = false")
    Orders findOrdersById(Long id);

    @Query(value = "select new com.computercomponent.api.dto.OrdersManagementDTO(o.id, o.name, o.description, o.shippingAddress, o.paymentMethod, o.totalQuantity, o.totalPrice, o.status, u.fullName) " +
            "from Orders as o " +
            "left join  User  u on o.userId = u.id " +
            "where o.name like concat('%', :searchField, '%') " +
            "or  o.paymentMethod like concat('%', :searchField, '%') " +
            "or o.shippingAddress like concat('%', :searchField, '%') " +
            "and (:status is null or o.status = :status)" +
            "and (:userId is null or o.userId = :userId)",
            countQuery = "select count(o) from Orders as o " +
                    "left join  User  u on o.userId = u.id " +
                    "where o.name like concat('%', :searchField, '%') " +
                    "or  o.paymentMethod like concat('%', :searchField, '%') " +
                    "or o.shippingAddress like concat('%', :searchField, '%') " +
                    "and (:status is null or o.status = :status)" +
                    "and (:userId is null or o.userId = :userId)")
    Page<OrdersManagementDTO> findAllAndSearch(String searchField, Integer status, Long userId, Pageable pageable);

    @Query(value = "select new com.computercomponent.api.response.OrderDetail(o.id, o.name, o.description, o.shippingAddress, o.paymentMethod, o.totalQuantity, o.totalPrice, o.status, u.fullName) " +
            "from Orders as o " +
            "left join  User u on o.userId = u.id " +
            "where o.id = :id ",
            countQuery = "select count(o) from Orders as o " +
                    "left join  User u on o.userId = u.id " +
                    "where o.id = :id ")
    OrderDetail getDetail(Long id);
}
