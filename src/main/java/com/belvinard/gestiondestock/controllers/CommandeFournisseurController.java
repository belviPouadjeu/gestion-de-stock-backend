package com.belvinard.gestiondestock.controllers;

import com.belvinard.gestiondestock.dtos.CommandeFournisseurDTO;
import com.belvinard.gestiondestock.models.EtatCommande;
import com.belvinard.gestiondestock.responses.MyErrorResponses;
import com.belvinard.gestiondestock.services.CommandeFournisseurService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/commandes-fournisseur")
@Tag(name = "Commandes Fournisseur", description = "Gestion des commandes fournisseurs")
public class CommandeFournisseurController {

    private final CommandeFournisseurService commandeFournisseurService;

    public CommandeFournisseurController(CommandeFournisseurService commandeFournisseurService) {
        this.commandeFournisseurService = commandeFournisseurService;
    }

    /**
     * Sauvegarder une nouvelle commande fournisseur.
     * 
     * @param dto CommandeFournisseurDTO
     * @param fournisseurId L'ID du fournisseur
     * @return CommandeFournisseurDTO
     */
    @Operation(summary = "Créer une nouvelle commande fournisseur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Commande fournisseur créée avec succès"),
            @ApiResponse(responseCode = "404", description = "Fournisseur ou entreprise non trouvée")
    })
    @PostMapping("/{fournisseurId}")
    public ResponseEntity<CommandeFournisseurDTO> save(
            @Valid @RequestBody CommandeFournisseurDTO dto,
            @PathVariable Long fournisseurId) {

        CommandeFournisseurDTO createdCommande = commandeFournisseurService.save(dto, fournisseurId);

        return new ResponseEntity<>(createdCommande, HttpStatus.CREATED);
    }

    /**
     * Trouver une commande fournisseur par son ID.
     * 
     * @param id L'ID de la commande fournisseur
     * @return CommandeFournisseurDTO
     */
    @Operation(summary = "Trouver une commande fournisseur par ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Commande fournisseur trouvée"),
            @ApiResponse(responseCode = "404", description = "Commande fournisseur non trouvée")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CommandeFournisseurDTO> findById(@PathVariable Long id) {
        CommandeFournisseurDTO commande = commandeFournisseurService.findById(id);
        return ResponseEntity.ok(commande);
    }

    /**
     * Trouver une commande fournisseur par son code.
     * 
     * @param code Le code de la commande fournisseur
     * @return CommandeFournisseurDTO
     */
    @Operation(summary = "Trouver une commande fournisseur par code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Commande fournisseur trouvée"),
            @ApiResponse(responseCode = "404", description = "Commande fournisseur non trouvée")
    })
    @GetMapping("/code/{code}")
    public ResponseEntity<CommandeFournisseurDTO> findByCode(@PathVariable String code) {
        CommandeFournisseurDTO commande = commandeFournisseurService.findByCode(code);
        return ResponseEntity.ok(commande);
    }

    /**
     * Trouver toutes les commandes fournisseur.
     * 
     * @return Liste de CommandeFournisseurDTO
     */
    @Operation(summary = "Trouver toutes les commandes fournisseurs")
    @ApiResponse(responseCode = "200", description = "Liste des commandes fournisseurs")
    @GetMapping
    public ResponseEntity<List<CommandeFournisseurDTO>> findAll() {
        List<CommandeFournisseurDTO> commandes = commandeFournisseurService.findAll();
        return ResponseEntity.ok(commandes);
    }

    /**
     * Supprimer une commande fournisseur par son ID.
     * 
     * @param id L'ID de la commande fournisseur
     */
    @Operation(summary = "Supprimer une commande fournisseur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Commande fournisseur supprimée avec succès"),
            @ApiResponse(responseCode = "404", description = "Commande fournisseur non trouvée")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        commandeFournisseurService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Mettre à jour l'état d'une commande fournisseur.
     * 
     * @param idCommande L'ID de la commande fournisseur
     * @param nouvelEtat Le nouvel état de la commande
     * @return CommandeFournisseurDTO
     */
    @Operation(summary = "Mettre à jour l'état d'une commande fournisseur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "État de la commande fournisseur mis à jour"),
            @ApiResponse(responseCode = "404", description = "Commande fournisseur non trouvée")
    })
    @PatchMapping("/{idCommande}")
    public ResponseEntity<CommandeFournisseurDTO> updateEtatCommande(
            @PathVariable Long idCommande,
            @RequestParam EtatCommande nouvelEtat) {
        CommandeFournisseurDTO updatedCommande = commandeFournisseurService.updateEtatCommande(idCommande, nouvelEtat);
        return ResponseEntity.ok(updatedCommande);
    }

    /**
     * Trouver toutes les commandes pour un fournisseur donné.
     * 
     * @param fournisseurId L'ID du fournisseur
     * @return Liste de CommandeFournisseurDTO
     */
    @Operation(summary = "Trouver toutes les commandes d'un fournisseur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des commandes fournisseur"),
            @ApiResponse(responseCode = "404", description = "Fournisseur non trouvé")
    })
    @GetMapping("/fournisseur/{fournisseurId}")
    public ResponseEntity<List<CommandeFournisseurDTO>> findAllByFournisseurId(@PathVariable Long fournisseurId) {
        List<CommandeFournisseurDTO> commandes = commandeFournisseurService.findAllByFournisseurId(fournisseurId);
        return ResponseEntity.ok(commandes);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation ->
                errors.put(violation.getPropertyPath().toString(), violation.getMessage())
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
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
