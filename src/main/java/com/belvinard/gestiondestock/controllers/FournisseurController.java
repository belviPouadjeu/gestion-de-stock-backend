package com.belvinard.gestiondestock.controllers;

import com.belvinard.gestiondestock.dtos.FournisseurDTO;
import com.belvinard.gestiondestock.exceptions.APIException;
import com.belvinard.gestiondestock.responses.MyErrorResponses;
import com.belvinard.gestiondestock.services.FournisseurService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/fournisseurs")
@Tag(name = "Fournisseurs", description = "Opérations de gestion des fournisseurs")
public class FournisseurController {

    private final FournisseurService fournisseurService;

    public FournisseurController(FournisseurService fournisseurService) {
        this.fournisseurService = fournisseurService;
    }

    /**
     * Crée un nouveau fournisseur.
     *
     * @param dto les informations du fournisseur à créer
     * @return le fournisseur créé
     */
    @PostMapping("/entreprise/{entrepriseId}")
    @Operation(summary = "Créer un fournisseur", description = "Ajoute un nouveau fournisseur lié à une entreprise")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Fournisseur créé avec succès"),
            @ApiResponse(responseCode = "404", description = "Entreprise non trouvée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public ResponseEntity<FournisseurDTO> createFournisseur(
            @PathVariable Long entrepriseId,
            @Valid @RequestBody FournisseurDTO fournisseurDTO) {
        FournisseurDTO created = fournisseurService.createFournisseur(entrepriseId, fournisseurDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }


    /**
     * Recherche un fournisseur par son ID.
     *
     * @param id l'identifiant du fournisseur
     * @return le fournisseur correspondant
     */
    @GetMapping("/{id}")
    @Operation(summary = "Rechercher un fournisseur par ID",
            description = "Cette opération permet de rechercher un fournisseur à partir de son identifiant.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Fournisseur trouvé"),
        @ApiResponse(responseCode = "404", description = "Fournisseur non trouvé")
    })
    public ResponseEntity<FournisseurDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(fournisseurService.findById(id));
    }

    /**
     * Liste tous les fournisseurs enregistrés.
     *
     * @return la liste des fournisseurs
     */
    @GetMapping
    @Operation(summary = "Lister tous les fournisseurs",
            description = "Cette opération permet de récupérer la liste de tous les fournisseurs enregistrés.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des fournisseurs retournée avec succès")
    })
    public ResponseEntity<List<FournisseurDTO>> findAll() {

        return ResponseEntity.ok(fournisseurService.findAll());
    }

    /**
     * Supprime un fournisseur par son ID.
     *
     * @param id identifiant du fournisseur à supprimer
     * @return le fournisseur supprimé
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un fournisseur",
            description = "Cette opération permet de supprimer un fournisseur à partir de son identifiant.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Fournisseur supprimé avec succès"),
        @ApiResponse(responseCode = "404", description = "Fournisseur non trouvé")
    })
    public ResponseEntity<FournisseurDTO> delete(@PathVariable Long id) {
        return ResponseEntity.ok(fournisseurService.delete(id));
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<MyErrorResponses> myAPIException(APIException ex) {
        MyErrorResponses errorResponse = new MyErrorResponses("BAD_REQUEST", ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // ✅ Handle validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MyErrorResponses> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());  // Collect field errors
        }

        MyErrorResponses errorResponse = new MyErrorResponses(
                "BAD_REQUEST",
                "Validation failed. Please correct the errors.",
                errors
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
