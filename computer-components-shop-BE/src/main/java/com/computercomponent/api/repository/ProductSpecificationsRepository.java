package com.computercomponent.api.repository;

import com.computercomponent.api.dto.ProductSpecificationsManagementDTO;
import com.computercomponent.api.entity.ProductSpecifications;
import com.computercomponent.api.response.ProductSpecificationDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductSpecificationsRepository extends JpaRepository<ProductSpecifications, Long> {
    @Query(value = "select ps from ProductSpecifications ps  where ps.name = :name and ps.deleted = false")
    ProductSpecifications findProductSpecificationsByName(String name);

    @Query(value = "select ps from ProductSpecifications ps  where ps.id = :id and ps.deleted = false")
    ProductSpecifications findProductSpecificationsById(Long id);

    @Query(value = "select new com.computercomponent.api.dto.ProductSpecificationsManagementDTO(ps.id, ps.name, ps.value, ps.description, p.name, ps.status, ps.priority) " +
            "from ProductSpecifications as ps " +
            "left join  Products  p on ps.productId = p.id " +
            "where ps.name like concat('%', :searchField, '%') " +
            "or  p.name like concat('%', :searchField, '%') " +
            "and (:status is null or ps.status = :status)",
            countQuery = "select count(ps) from ProductSpecifications as ps " +
                    "left join  Products p on ps.productId = p.id " +
                    "where ps.name like concat('%', :searchField, '%') " +
                    "or  p.name like concat('%', :searchField, '%') " +
                    "and (:status is null or ps.status = :status)")
    Page<ProductSpecificationsManagementDTO> findAllAndSearch(String searchField, Integer status, Pageable pageable);

    @Query("select new com.computercomponent.api.response.ProductSpecificationDetail(ps.id, ps.name, ps.value, ps.priority, ps.status) " +
            "from ProductSpecifications ps " +
            "where ps.productId = :id")
    List<ProductSpecificationDetail> getDetail(Long id);
}
