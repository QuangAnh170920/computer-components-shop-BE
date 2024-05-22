package com.computercomponent.api.repository;

import com.computercomponent.api.dto.BrandDropListDTO;
import com.computercomponent.api.dto.BrandManagementDTO;
import com.computercomponent.api.entity.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    @Query(value = "select br from Brand br  where br.name = :name and br.deleted = false")
    Brand findBrandByName(String name);

    @Query(value = "select new com.computercomponent.api.dto.BrandManagementDTO(br.id, br.name, br.description, br.status) from Brand as br where br.name like concat('%', :name, '%')",
            countQuery = "select count(br) from Brand as br where br.name like concat('%', :name, '%')")
    Page<BrandManagementDTO> findAllAndSearch(String name, Pageable pageable);

    @Query(value = "select br from Brand br  where br.id = :id and br.deleted = false")
    Brand findBrandById(Long id);

    @Query("select new com.computercomponent.api.dto.BrandDropListDTO(b.id, b.name) from Brand b where b.deleted = false order by b.createdAt DESC ")
    List<BrandDropListDTO> dropList();
}
