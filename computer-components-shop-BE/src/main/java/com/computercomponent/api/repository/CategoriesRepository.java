package com.computercomponent.api.repository;

import com.computercomponent.api.dto.CategoriesManagementDTO;
import com.computercomponent.api.dto.CategoryDropListDTO;
import com.computercomponent.api.entity.Brand;
import com.computercomponent.api.entity.Categories;
import com.computercomponent.api.response.BrandDetail;
import com.computercomponent.api.response.CategoriesDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Long> {
    @Query(value = "select cg from Categories cg  where cg.name = :name and cg.deleted = false")
    Categories findCategoriesByName(String name);

    @Query(value = "select cg from Categories cg  where cg.code = :code and cg.deleted = false")
    Categories findCategoriesByCode(String code);

    @Query(value = "select new com.computercomponent.api.dto.CategoriesManagementDTO(cg.id, cg.code, cg.name, cg.description, cg.status) " +
            "from Categories as cg where " +
            "cg.name like concat('%', :searchField, '%')" +
            "or cg.code like concat('%', :searchField, '%')" +
            "and (:status is null or cg.status = :status)",
            countQuery = "select count(cg) from Categories as cg " +
                    "where cg.name like concat('%', :searchField, '%')" +
                    "or cg.code like  concat('%', :searchField, '%') " +
                    "and (:status is null or cg.status = :status)")
    Page<CategoriesManagementDTO> findAllAndSearch(String searchField, Integer status, Pageable pageable);

    @Query(value = "select cg from Categories cg  where cg.id = :id and cg.deleted = false")
    Categories findCategoriesById(Long id);

    @Query("select new com.computercomponent.api.dto.CategoryDropListDTO(c.id, c.name) from Categories c where c.deleted = false order by c.createdAt DESC ")
    List<CategoryDropListDTO> dropList();

    @Query(value = "select new com.computercomponent.api.response.CategoriesDetail(c.id, c.code, c.name, c.description, c.status) " +
            "from Categories as c " +
            "where c.id = :id ",
            countQuery = "select count(c) from Categories as c " +
                    "where c.id = :id")
    CategoriesDetail getDetail(Long id);
}
