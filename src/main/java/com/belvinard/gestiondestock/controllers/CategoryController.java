package com.belvinard.gestiondestock.controllers;

import com.belvinard.gestiondestock.dtos.CategoryDTO;
import com.belvinard.gestiondestock.exceptions.APIException;
import com.belvinard.gestiondestock.exceptions.DuplicateEntityException;
import com.belvinard.gestiondestock.exceptions.ResourceNotFoundException;
import com.belvinard.gestiondestock.responses.CategoryResponse;
import com.belvinard.gestiondestock.responses.ErrorResponse;
import com.belvinard.gestiondestock.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/public/categories")
@RequiredArgsConstructor
@Tag(name = "Catégories", description = "API de gestion des catégories")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "Rechercher une catégorie par désignation")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Catégorie trouvée"),
            @ApiResponse(responseCode = "404", description = "Catégorie non trouvée")
    })
    @GetMapping("/designation/{designation}")
    public ResponseEntity<CategoryDTO> getCategoryByDesignation(@PathVariable String designation) {
        CategoryDTO category = categoryService.findByDesignation(designation);
        return ResponseEntity.ok(category);
    }


    @Operation(
        summary = "Créer une nouvelle catégorie",
        description = "Ajoute une nouvelle catégorie à une entreprise existante"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Catégorie créée avec succès"),
        @ApiResponse(responseCode = "400", description = "Données invalides"),
        @ApiResponse(responseCode = "404", description = "Entreprise non trouvée")
    })
    @PostMapping("/{entrepriseId}")
    public ResponseEntity<CategoryDTO> createCategory(
            @Parameter(description = "ID de l'entreprise à laquelle ajouter la catégorie") @PathVariable Long entrepriseId,
            @Parameter(description = "Les données de la catégorie à créer") @Valid @RequestBody CategoryDTO categoryDTO) {

        CategoryDTO created = categoryService.addCategory(entrepriseId, categoryDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @Operation(summary = "Lister toutes les catégories avec les entreprises associees")
    @ApiResponse(responseCode = "200", description = "Liste des catégories récupérée avec succès")
    @GetMapping
    public ResponseEntity<CategoryResponse> getAllCategories() {
        CategoryResponse categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @Operation(summary = "Rechercher une catégorie par ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Catégorie trouvée"),
            @ApiResponse(responseCode = "404", description = "Catégorie non trouvée")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        CategoryDTO category = categoryService.findById(id);
        return ResponseEntity.ok(category);
    }


    @Operation(summary = "Supprimer une catégorie")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Catégorie supprimée"),
            @ApiResponse(responseCode = "404", description = "Catégorie non trouvée")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long id) {
        CategoryDTO deleted = categoryService.delete(id);
        return ResponseEntity.ok(deleted);
    }



}
