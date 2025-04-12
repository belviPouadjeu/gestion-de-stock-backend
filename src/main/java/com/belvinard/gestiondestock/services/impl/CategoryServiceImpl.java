package com.belvinard.gestiondestock.services.impl;

import com.belvinard.gestiondestock.dtos.CategoryDTO;
import com.belvinard.gestiondestock.exceptions.APIException;
import com.belvinard.gestiondestock.exceptions.DuplicateEntityException;
import com.belvinard.gestiondestock.exceptions.ResourceNotFoundException;
import com.belvinard.gestiondestock.models.Category;
import com.belvinard.gestiondestock.models.Entreprise;
import com.belvinard.gestiondestock.repositories.CategoryRepository;
import com.belvinard.gestiondestock.repositories.EntepriseRepository;
import com.belvinard.gestiondestock.responses.CategoryResponse;
import com.belvinard.gestiondestock.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final EntepriseRepository entepriseRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper, EntepriseRepository entepriseRepository) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.entepriseRepository = entepriseRepository;
    }

    @Override
    public CategoryDTO addCategory(Long entrepriseId, CategoryDTO categoryDTO) {
        List<Category> existing = categoryRepository.findByCode(categoryDTO.getCode());

        if (!existing.isEmpty()) {
            throw new DuplicateEntityException("Une catégorie avec le code " + categoryDTO.getCode() + " existe déjà.");
        }

        Entreprise entreprise = entepriseRepository.findById(entrepriseId)
                .orElseThrow(() -> new ResourceNotFoundException("Entreprise", "id", entrepriseId));

        Category category = modelMapper.map(categoryDTO, Category.class);
        category.setEntreprise(entreprise);

        Category saved = categoryRepository.save(category);
        return modelMapper.map(saved, CategoryDTO.class);
    }

//    @Override
//    public CategoryDTO findByDesignation(String designation) {
//        List<Category> categories = categoryRepository.findByDesignation(designation);
//
//        if (categories.isEmpty()) {
//            throw new ResourceNotFoundException("Category", "designation", designation);
//        }
//
//        // Si plusieurs, on retourne la première. Sinon, on peut aussi renvoyer une liste de DTO si besoin.
//        return modelMapper.map(categories.get(0), CategoryDTO.class);
//    }

    @Override
    public CategoryDTO findByDesignation(String designation) {
        List<Category> categories = categoryRepository.findByDesignationIgnoreCase(designation);

        if (categories.isEmpty()) {
            throw new ResourceNotFoundException("Category", "designation", designation);
        }

        return modelMapper.map(categories.getFirst(), CategoryDTO.class);
    }




    @Override
    public CategoryResponse getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            throw new APIException("No Category created untill now !!!");
        }

        List<CategoryDTO> categoryDTOS = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        return categoryResponse;
    }


    @Override
    public CategoryDTO findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "not found with id", id));

        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setDesignation(category.getDesignation());
        dto.setCode(category.getCode());
        dto.setEntrepriseId(category.getEntreprise().getId());
        dto.setCreationDate(category.getCreationDate());
        dto.setLastModifiedDate(category.getLastModifiedDate());

        return dto;
    }



    @Override
    public CategoryDTO delete(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "not found with id", id)); // ✅ NE PAS convertir

        categoryRepository.delete(category);

        return modelMapper.map(category, CategoryDTO.class);
    }


}
