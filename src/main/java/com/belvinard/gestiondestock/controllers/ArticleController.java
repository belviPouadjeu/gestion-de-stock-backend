package com.belvinard.gestiondestock.controllers;

import com.belvinard.gestiondestock.dtos.ArticleDTO;
import com.belvinard.gestiondestock.services.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


}
