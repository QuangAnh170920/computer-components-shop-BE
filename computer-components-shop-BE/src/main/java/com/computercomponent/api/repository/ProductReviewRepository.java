package com.computercomponent.api.repository;

import com.computercomponent.api.dto.BrandDropListDTO;
import com.computercomponent.api.dto.BrandManagementDTO;
import com.computercomponent.api.dto.ProductReviewManagementDTO;
import com.computercomponent.api.entity.Brand;
import com.computercomponent.api.entity.ProductReviews;
import com.computercomponent.api.response.BrandDetail;
import com.computercomponent.api.response.ProductReviewDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReviews, Long> {

    @Query(value = "select new com.computercomponent.api.dto.ProductReviewManagementDTO(pr.id, p.name, u.fullName, pr.comment, pr.rate)" +
            "from ProductReviews as pr " +
            "left join  Products p on pr.productId = p.id " +
            "left join User as u on pr.userId = u.id " +
            "where ((:userId is null or pr.userId = :userId) " +
            "and  (:productId is null or pr.productId = :productId)) " +
            "and pr.deleted = false",
            countQuery = "select count(pr) from ProductReviews as pr " +
                    "left join  Products p on pr.productId = p.id  " +
                    "left join User as u on pr.userId = u.id " +
                    "where ((:userId is null or pr.userId = :userId) " +
                    "and  (:productId is null or pr.productId = :productId)) " +
                    "and pr.deleted = false")
    Page<ProductReviewManagementDTO> findAllAndSearch(Long productId, Long userId, Pageable pageable);

    @Query(value = "select pr from ProductReviews pr  where pr.id = :id and pr.deleted = false")
    ProductReviews findProductReviewsById(Long id);

    @Query(value = "select new com.computercomponent.api.response.ProductReviewDetail(pr.id, pr.productId, pr.userId, pr.comment, pr.rate) " +
            "from ProductReviews as pr " +
            "where pr.id = :id ",
            countQuery = "select count(pr) from ProductReviews as pr " +
                    "where pr.id = :id")
    ProductReviewDetail getDetail(Long id);
}
