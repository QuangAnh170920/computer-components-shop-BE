package com.computercomponent.api.repository;

import com.computercomponent.api.dto.ProductImageManagementDTO;
import com.computercomponent.api.entity.ProductImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    @Query(value = "select pi from ProductImage pi  where pi.imageUrl = :imageUrl and pi.deleted = false")
    ProductImage findProductImageByImageUrl(String imageUrl);

    @Query(value = "select pi from ProductImage pi  where pi.id = :id and pi.deleted = false")
    ProductImage findProductImageById(Long id);

    @Query(value = "select new com.computercomponent.api.dto.ProductImageManagementDTO(pi.id, pi.imageUrl, pi.description, p.name, pi.status) " +
            "from ProductImage as pi " +
            "left join  Products  p on pi.productId = p.id " +
            "or  p.name like concat('%', :searchField, '%') " +
            "and (:status is null or pi.status = :status)",
            countQuery = "select count(pi) from ProductImage as pi " +
                    "left join  Products p on pi.productId = p.id " +
                    "or  p.name like concat('%', :searchField, '%') " +
                    "and (:status is null or pi.status = :status)")
    Page<ProductImageManagementDTO> findAllAndSearch(String searchField, Integer status, Pageable pageable);
}
