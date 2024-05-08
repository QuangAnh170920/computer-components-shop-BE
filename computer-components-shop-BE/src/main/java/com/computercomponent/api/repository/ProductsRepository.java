package com.computercomponent.api.repository;

import com.computercomponent.api.common.ProductsStatus;
import com.computercomponent.api.dto.ProductsManagementDTO;
import com.computercomponent.api.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Long> {
    @Query(value = "select prod from Products prod  where prod.name = :name and prod.deleted = false")
    Products findProductsByName(String name);

    @Query(value = "select new com.computercomponent.api.dto.ProductsManagementDTO(p.id, p.name, p.description, b.name, c.name, p.price, p.status) " +
            "from Products as p " +
            "left join  Brand  b on p.brandId = b.id " +
            "left join Categories as c on p.categoryId = c.id " +
            "where p.name like concat('%', :searchField, '%') " +
            "or  c.name like concat('%', :searchField, '%') " +
            "or b.name like concat('%', :searchField, '%') " +
            "and (:status is null or p.status = :status)",
            countQuery = "select count(p) from Products as p " +
                    "left join  Brand  b on p.brandId = b.id " +
                    "left join Categories as c on p.categoryId = c.id " +
                    "where p.name like concat('%', :searchField, '%') " +
                    "or  c.name like concat('%', :searchField, '%') " +
                    "or b.name like concat('%', :searchField, '%') " +
                    "and (:status is null or p.status = :status)")
    Page<ProductsManagementDTO> findAllAndSearch(String searchField, Integer status, Pageable pageable);

    @Query(value = "select prod from Products prod  where prod.id = :id and prod.deleted = false")
    Products findProductsById(Long id);
}
