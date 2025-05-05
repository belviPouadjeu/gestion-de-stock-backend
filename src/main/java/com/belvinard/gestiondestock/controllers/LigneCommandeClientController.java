
package com.belvinard.gestiondestock.controllers;

import com.belvinard.gestiondestock.dtos.LigneCommandeClientDTO;
import com.belvinard.gestiondestock.exceptions.ResourceNotFoundException;
import com.belvinard.gestiondestock.responses.ErrorResponse;
import com.belvinard.gestiondestock.services.LigneCommandeClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ligne-commande-clients")
@RequiredArgsConstructor
public class LigneCommandeClientController {

    private final LigneCommandeClientService ligneCommandeClientService;

    /**
     * Crée une ligne de commande client associée à une commande et un article.
     *
     * @param commandeId L'identifiant de la commande.
     * @param articleId L'identifiant de l'article.
     * @param dto Les données de la ligne à créer.
     * @return La ligne de commande créée.
     */
    @PostMapping("/{commandeId}/articles/{articleId}/lignes")
    public ResponseEntity<LigneCommandeClientDTO> createLigneCommandeClient(
            @PathVariable Long commandeId,
            @PathVariable Long articleId,
            @Valid @RequestBody LigneCommandeClientDTO ligneDTO) {

        LigneCommandeClientDTO created = ligneCommandeClientService
                .createLigneCommandeClient(commandeId, articleId, ligneDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    /**
     * Enregistre plusieurs lignes de commande pour une commande donnée.
     *
     * @param commandeId L'identifiant de la commande.
     * @param lignesDTO La liste des lignes à enregistrer.
     * @return Les lignes de commande enregistrées.
     */
    @PostMapping("/{commandeId}/bulk")
    public ResponseEntity<List<LigneCommandeClientDTO>> saveAllLignes(
            @PathVariable Long commandeId,
            @RequestBody @Valid List<LigneCommandeClientDTO> lignesDTO) {

        return ResponseEntity.ok(ligneCommandeClientService.saveAll(commandeId, lignesDTO));
    }

    /**
     * 🔁 Met à jour une ligne de commande existante.
     */
    @PutMapping("/{ligneId}")
    @Operation(summary = "Mettre à jour une ligne de commande client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ligne de commande mise à jour avec succès"),
            @ApiResponse(responseCode = "404", description = "Ligne de commande non trouvée")
    })
    public ResponseEntity<LigneCommandeClientDTO> updateLigne(
            @PathVariable Long ligneId,
            @RequestBody @Valid LigneCommandeClientDTO ligneDTO) {
        return ResponseEntity.ok(ligneCommandeClientService.update(ligneId, ligneDTO));
    }

    /**
     * 🗑️ Supprime une ligne de commande client par ID.
     */
    @DeleteMapping("/{ligneId}")
    @Operation(summary = "Supprimer une ligne de commande client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ligne supprimée avec succès"),
            @ApiResponse(responseCode = "404", description = "Ligne non trouvée")
    })
    public ResponseEntity<LigneCommandeClientDTO> deleteLigne(@PathVariable Long ligneId) {
        return ResponseEntity.ok(ligneCommandeClientService.delete(ligneId));
    }

    /**
     * 🔍 Récupère une ligne de commande client par ID.
     */
    @GetMapping("/{ligneId}")
    @Operation(summary = "Récupérer une ligne de commande client par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ligne trouvée"),
            @ApiResponse(responseCode = "404", description = "Ligne non trouvée")
    })
    public ResponseEntity<LigneCommandeClientDTO> getById(@PathVariable Long ligneId) {
        return ResponseEntity.ok(ligneCommandeClientService.findById(ligneId));
    }

    /**
     * 📄 Récupère toutes les lignes de commandes clients.
     */
    @GetMapping
    @Operation(summary = "Lister toutes les lignes de commande client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des lignes récupérée")
    })
    public ResponseEntity<List<LigneCommandeClientDTO>> getAll() {
        return ResponseEntity.ok(ligneCommandeClientService.findAll());
    }


}
