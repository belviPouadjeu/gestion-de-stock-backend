package com.belvinard.gestiondestock.repositories;

import com.belvinard.gestiondestock.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    //Optional<Category> findByDesignation(String designation);

    List<Category> findByCode(String code);

    List<Category> findByDesignation(String designation);
    List<Category> findByDesignationIgnoreCase(String designation);

}
