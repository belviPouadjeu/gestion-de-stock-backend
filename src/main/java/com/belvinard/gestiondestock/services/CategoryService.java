package com.belvinard.gestiondestock.services;

import com.belvinard.gestiondestock.dtos.CategoryDTO;
import com.belvinard.gestiondestock.responses.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryDTO addCategory(Long id, CategoryDTO categoryDTO);
    CategoryDTO findByDesignation(String designation);

    CategoryResponse getAllCategories();
    CategoryDTO findById(Long id);

    //CategoryDTO findByCode(String code);
    CategoryDTO delete(Long id);

}
