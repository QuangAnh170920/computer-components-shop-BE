package com.computercomponent.api.repository;

import com.computercomponent.api.dto.ProductDropListDTO;
import com.computercomponent.api.dto.PromotionDropListDTO;
import com.computercomponent.api.dto.PromotionManagementDTO;
import com.computercomponent.api.entity.Promotion;
import com.computercomponent.api.response.PromotionDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    @Query(value = "select pr from Promotion pr  where pr.name = :name and pr.deleted = false")
    Promotion findPromotionByName(String name);

    @Query(value = "select pr from Promotion pr  where pr.code = :code and pr.deleted = false")
    Promotion findPromotionsByCode(String code);

    @Query(value = "select new com.computercomponent.api.dto.PromotionManagementDTO(pr.id, pr.code, pr.name, pr.price, pr.discountPercentage, pr.description, pr.status)" +
            " from Promotion as pr" +
            " where (pr.name like concat('%', :searchField, '%')" +
            "or pr.code like concat('%', :searchField, '%')) " +
            "and (:status is null or pr.status = :status)" +
            "and pr.deleted = false",
            countQuery = "select count(pr) from Promotion as pr " +
                    "where (pr.name like concat('%', :searchField, '%')" +
                    "or  pr.code like concat('%', :searchField, '%')) " +
                    "and (:status is null or pr.status = :status)" +
                    "and pr.deleted = false")
    Page<PromotionManagementDTO> findAllAndSearch(String searchField, Integer status, Pageable pageable);

    @Query(value = "select pr from Promotion pr  where pr.id = :id and pr.deleted = false")
    Promotion findPromotionById(Long id);

    @Query(value = "select new com.computercomponent.api.response.PromotionDetail(pr.id, pr.code, pr.name, pr.price, pr.discountPercentage, pr.description, pr.status) " +
            "from Promotion as pr " +
            "where pr.id = :id ",
            countQuery = "select count(pr) from Promotion as pr " +
                    "where pr.id = :id")
    PromotionDetail getDetail(Long id);

    @Query("select new com.computercomponent.api.dto.PromotionDropListDTO(p.id, p.name, p.discountPercentage) from Promotion p  where p.deleted = false order by p.createdAt DESC ")
    List<PromotionDropListDTO> dropList();
}
