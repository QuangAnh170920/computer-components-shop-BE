package com.computercomponent.api.repository;

import com.computercomponent.api.dto.CategoriesManagementDTO;
import com.computercomponent.api.entity.Categories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Long> {
    @Query(value = "select cg from Categories cg  where cg.name = :name and cg.deleted = false")
    Categories findCategoriesByName(String name);

    @Query(value = "select new com.computercomponent.api.dto.CategoriesManagementDTO(cg.id, cg.name, cg.description, cg.status) from Categories as cg where cg.name like concat('%', :name, '%')",
            countQuery = "select count(cg) from Categories as cg where cg.name like concat('%', :name, '%')")
    Page<CategoriesManagementDTO> findAllAndSearch(String name, Pageable pageable);

    @Query(value = "select cg from Categories cg  where cg.id = :id and cg.deleted = false")
    Categories findCategoriesById(Long id);
}
