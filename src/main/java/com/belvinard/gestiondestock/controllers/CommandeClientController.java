package com.belvinard.gestiondestock.controllers;

import com.belvinard.gestiondestock.dtos.CommandeClientDTO;
import com.belvinard.gestiondestock.dtos.LigneCommandeClientDTO;
import com.belvinard.gestiondestock.exceptions.ResourceNotFoundException;
import com.belvinard.gestiondestock.models.EtatCommande;
import com.belvinard.gestiondestock.responses.ErrorResponse;
import com.belvinard.gestiondestock.services.CommandeClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/commande-clients")
@RequiredArgsConstructor
public class CommandeClientController {

    private final CommandeClientService commandeClientService;

    /**
     * Enregistre une nouvelle commande pour un client donné.
     *
     * @param clientId L'identifiant du client auquel la commande est associée.
     * @param dto Les données de la commande à créer.
     * @return La commande créée.
     */
    @Operation(
            summary = "Créer une commande client",
            description = "Cette opération permet de créer une commande pour un client donné dans une entreprise spécifique."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Commande client créée avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides ou client/entreprise introuvable"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @PostMapping("/commande-clients/client/{clientId}/entreprise/{entrepriseId}")
    public ResponseEntity<CommandeClientDTO> createCommandeClient(
            @PathVariable Long clientId,
            @PathVariable Long entrepriseId,
            @Valid @RequestBody CommandeClientDTO commandeClientDTO
    ) {
        // Injection manuelle des paramètres dans le DTO
        commandeClientDTO.setClientId(clientId);
        commandeClientDTO.setEntrepriseId(entrepriseId);

        CommandeClientDTO createdCommande = commandeClientService.createCommandeClient(clientId, entrepriseId, commandeClientDTO);
        return new ResponseEntity<>(createdCommande, HttpStatus.CREATED);
    }

    /**
     * Met à jour l'état d'une commande client existante.
     *
     * @param idCommande L'identifiant de la commande à mettre à jour.
     * @param etatCommande Le nouvel état à appliquer à la commande (EN_PREPARATION, VALIDEE, LIVREE).
     * @return La commande mise à jour avec son nouvel état.
     */
    @PatchMapping("/{idCommande}/etat/{etatCommande}")
    @Operation(
            summary = "Mettre à jour l'état d'une commande client",
            description = "Cette opération permet de modifier l'état d'une commande (ex: EN_PREPARATION -> VALIDEE)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Commande mise à jour avec succès"),
            @ApiResponse(responseCode = "404", description = "Commande non trouvée")
    })
    public ResponseEntity<CommandeClientDTO> updateEtatCommande(
            @PathVariable Long idCommande,
            @PathVariable EtatCommande etatCommande
    ) {
        CommandeClientDTO updatedCommande = commandeClientService.updateEtatCommande(idCommande, etatCommande);
        return ResponseEntity.ok(updatedCommande);
    }

    /**
     * Récupère la liste de toutes les commandes clients.
     *
     * @return Une liste de toutes les commandes clients.
     */
    @GetMapping
    @Operation(summary = "Lister toutes les commandes clients", description = "Retourne toutes les commandes clients enregistrées")
    public ResponseEntity<List<CommandeClientDTO>> findAll() {
        return ResponseEntity.ok(commandeClientService.findAll());
    }

    /**
     * Récupère une commande client spécifique par son ID.
     *
     * @param id L'identifiant de la commande à rechercher.
     * @return La commande correspondante si elle existe.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Rechercher une commande client par ID", description = "Retourne une commande client à partir de son identifiant")
    public ResponseEntity<CommandeClientDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(commandeClientService.findById(id));
    }

    /**
     * Récupère toutes les lignes de commande d'une commande client spécifique.
     *
     * @param commandeId L'identifiant de la commande client.
     * @return La liste des lignes associées à cette commande.
     */
    @GetMapping("/{commandeId}/lignes")
    @Operation(summary = "Lister les lignes d'une commande client", description = "Retourne toutes les lignes de commande associées à une commande client")
    public ResponseEntity<List<LigneCommandeClientDTO>> findAllLignesCommandesClientByCommandeClientId(
            @PathVariable Long commandeId) {
        return ResponseEntity.ok(commandeClientService.findAllLignesCommandesClientByCommandeClientId(commandeId));
    }

    /**
     * Supprime une commande client spécifique.
     *
     * @param id L'identifiant de la commande à supprimer.
     * @return La commande supprimée.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une commande client", description = "Supprime une commande client par son identifiant")
    public ResponseEntity<CommandeClientDTO> deleteCommandeClient(@PathVariable Long id) {
        return ResponseEntity.ok(commandeClientService.deleteCommandeClient(id));
    }


}
