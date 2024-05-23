package com.computercomponent.api.repository;

import com.computercomponent.api.entity.OrderDetail;
import com.computercomponent.api.response.OrderDetailResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    @Query("select new com.computercomponent.api.response.OrderDetailResponse(od.id, od.orderId, p.name, od.quantity) " +
            "from OrderDetail od " +
            "left join  Products  p on od.productId = p.id " +
            "where od.orderId = :id")
    List<OrderDetailResponse> getDetail(Long id);
}
