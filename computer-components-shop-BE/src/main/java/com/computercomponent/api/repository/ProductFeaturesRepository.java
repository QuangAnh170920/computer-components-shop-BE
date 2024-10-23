package com.computercomponent.api.repository;

import com.computercomponent.api.dto.ProductFeaturesDTO;
import com.computercomponent.api.entity.ProductFeatures;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductFeaturesRepository extends JpaRepository<ProductFeatures, Long> {

    @Modifying
    @Query("DELETE FROM ProductFeatures pf WHERE pf.productId = ?1")
    void deleteByProductId(Long productId);

    @Query("select new com.computercomponent.api.dto.ProductFeaturesDTO(pf.productId, pf.feature, pf.priority) " +
            "from ProductFeatures pf " +
            "where pf.productId = :productId")
    List<ProductFeaturesDTO> findFeaturesByProductId(@Param("productId") Long productId);
}
