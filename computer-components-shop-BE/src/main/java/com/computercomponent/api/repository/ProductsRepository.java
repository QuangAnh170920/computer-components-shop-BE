package com.computercomponent.api.repository;

import com.computercomponent.api.common.ProductsStatus;
import com.computercomponent.api.dto.ProductDropListDTO;
import com.computercomponent.api.dto.ProductQuantityDTO;
import com.computercomponent.api.dto.ProductsManagementDTO;
import com.computercomponent.api.entity.Products;
import com.computercomponent.api.response.ProductDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Long> {
    @Query(value = "select prod from Products prod  where prod.name = :name and prod.deleted = false")
    Products findProductsByName(String name);

    @Query(value = "select prod from Products prod  where prod.code = :code and prod.deleted = false")
    Products findProductsByCode(String code);

    @Query(value = "select new com.computercomponent.api.dto.ProductsManagementDTO(p.id, p.code, p.name, p.description, p.price, p.quantityAvailable, p.status, c.name, p.finalTotalPrice) " +
            "from Products as p " +
            "left join Categories as c on p.categoryId = c.id " +
            "where p.name like concat('%', :searchField, '%') " +
            "or  p.code like concat('%', :searchField, '%') " +
            "or  c.name like concat('%', :searchField, '%') " +
            "and (:status is null or p.status = :status)",
            countQuery = "select count(p) from Products as p " +
                    "left join Categories as c on p.categoryId = c.id " +
                    "where p.name like concat('%', :searchField, '%') " +
                    "or  p.code like concat('%', :searchField, '%') " +
                    "or  c.name like concat('%', :searchField, '%') " +
                    "and (:status is null or p.status = :status)")
    Page<ProductsManagementDTO> findAllAndSearch(String searchField, Integer status, Pageable pageable);

    @Query(value = "select prod from Products prod  where prod.id = :id and prod.deleted = false")
    Products findProductsById(Long id);

    @Query(value = "select new com.computercomponent.api.response.ProductDetail(p.id, p.name, p.description, p.price, p.quantityAvailable, p.status, c.name, p.finalTotalPrice) " +
            "from Products as p " +
            "left join Categories as c on p.categoryId = c.id " +
            "where p.id = :id ",
            countQuery = "select count(p) from Products as p " +
                    "left join Categories as c on p.categoryId = c.id " +
                    "where p.id = :id")
    ProductDetail getDetail(Long id);

    @Query("select new com.computercomponent.api.dto.ProductDropListDTO(p.id, p.name) from Products p  where p.deleted = false order by p.createdAt DESC ")
    List<ProductDropListDTO> dropList();

    @Query(value = "select new com.computercomponent.api.dto.ProductQuantityDTO(p.id, p.code, p.name, p.quantityAvailable, p.updatedAt) " +
            "from Products as p " +
            "where p.name like concat('%', :searchField, '%') " +
            "or  p.code like concat('%', :searchField, '%') " +
            "and (:status is null or p.status = :status)",
            countQuery = "select count(p) from Products as p " +
                    "where p.name like concat('%', :searchField, '%') " +
                    "or  p.code like concat('%', :searchField, '%') " +
                    "and (:status is null or p.status = :status)")
    Page<ProductQuantityDTO> getProductQuantityList(String searchField, Integer status, Pageable pageable);

    @Query("select count(p) > 0 from Products p where p.categoryId = :categoryId and p.deleted = false")
    boolean existsByCategoryId(Long categoryId);
}
