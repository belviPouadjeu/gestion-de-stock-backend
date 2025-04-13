package com.belvinard.gestiondestock.controllers;

import com.belvinard.gestiondestock.dtos.ArticleDTO;
import com.belvinard.gestiondestock.exceptions.ResourceNotFoundException;
import com.belvinard.gestiondestock.responses.MyErrorResponses;
import com.belvinard.gestiondestock.services.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
@Tag(name = "Articles", description = "API de gestion des articles")
public class ArticleController {

    private final ArticleService articleService;

    /* ================== CREATE ARTICLE ================== */
    @Operation(summary = "Créer un nouvel article")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Article créé avec succès"),
            @ApiResponse(responseCode = "404", description = "Entreprise ou catégorie non trouvée")
    })
    @PostMapping("/entreprises/{entrepriseId}/categories/{categoryId}/articles")
    public ResponseEntity<ArticleDTO> createArticle(
            @PathVariable Long entrepriseId,
            @PathVariable Long categoryId,
            @RequestBody ArticleDTO articleDTO
    ) {
        ArticleDTO createdArticle = articleService.createArticle(entrepriseId, categoryId, articleDTO);
        return new ResponseEntity<>(createdArticle, HttpStatus.CREATED);
    }

    /* ================== GET ALL ARTICLES ================== */
    @Operation(summary = "Récupérer la liste de tous les articles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des articles récupérée avec succès")
    })
    @GetMapping("/articles")
    public ResponseEntity<List<ArticleDTO>> getAllArticles() {
        List<ArticleDTO> articles = articleService.getAllArticles();
        return ResponseEntity.ok(articles);
    }

    /* ================== GET ARTICLES BY ID ================== */

    @Operation(summary = "Récupérer un article par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Article trouvé avec succès"),
            @ApiResponse(responseCode = "404", description = "Article non trouvé")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ArticleDTO> getArticleById(
            @Parameter(description = "ID de l'article à récupérer", required = true)
            @PathVariable Long id) {
        ArticleDTO articleDTO = articleService.findById(id);
        return ResponseEntity.ok(articleDTO);
    }

    /* ================== DELETE ARTICLE ================== */
    @Operation(summary = "Supprimer un article par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Article supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Article non trouvé")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ArticleDTO> deleteArticle(
            @Parameter(description = "ID de l'article à supprimer", required = true)
            @PathVariable Long id) {
        ArticleDTO deleted = articleService.deleteArticle(id);
        return ResponseEntity.ok(deleted);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<MyErrorResponses> handleResourceNotFoundException(ResourceNotFoundException ex) {
        MyErrorResponses errorResponse = new MyErrorResponses("NOT_FOUND", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation ->
                errors.put(violation.getPropertyPath().toString(), violation.getMessage())
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }


}
