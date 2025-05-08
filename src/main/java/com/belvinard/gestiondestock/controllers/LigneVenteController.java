package com.belvinard.gestiondestock.controllers;

import com.belvinard.gestiondestock.dtos.LigneVenteDTO;
import com.belvinard.gestiondestock.responses.LigneVentResponse;
import com.belvinard.gestiondestock.services.LigneVenteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ligneVente")
@Tag(name = "Lignes de vente", description = "API de gestion des ligne de ventes")
public class LigneVenteController {

    @Autowired
    private LigneVenteService ligneVenteService;

    @Operation(summary = "Récupérer la liste de tous les ligne de ventes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des ligne de ventesrécupérée avec succès")

    })
    @GetMapping("/allLignes")
    public ResponseEntity<LigneVentResponse> getAllLigneVente(){
        LigneVentResponse ligneVentResponse = ligneVenteService.getAllLigneVente();
        return new ResponseEntity<>(ligneVentResponse, HttpStatus.OK);
    }

    @PostMapping("/vente/{venteId}/article/{articleId}")
    @Operation(summary = "Créer une ligne de vente", description = "Enregistre une nouvelle ligne de vente pour une entreprise")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lignes de vente créée avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public ResponseEntity<LigneVenteDTO> createLigneVente(@RequestBody LigneVenteDTO ligneVenteDTO, @PathVariable Long venteId,
                                                          @PathVariable Long articleId){
       LigneVenteDTO savedLigneVente = ligneVenteService.createLigneVente(ligneVenteDTO, venteId, articleId);

       return new ResponseEntity<>(savedLigneVente, HttpStatus.CREATED);

    }

    @GetMapping("/vente/{venteId}")
    @Operation(
            summary = "Obtenir toutes les lignes de vente par ID de vente",
            description = "Retourne toutes les lignes de vente associées à un identifiant de vente spécifique"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des lignes de vente récupérée avec succès"),
            @ApiResponse(responseCode = "404", description = "Vente non trouvée"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public LigneVentResponse getAllLigneVentesByVenteId(
            @Parameter(description = "Identifiant de la vente", required = true, example = "1")
            @PathVariable Long venteId
    ) {
        return ligneVenteService.findAllByVenteId(venteId);
    }

    @Operation(
            summary = "Modifier une ligne de vente",
            description = "Met à jour une ligne de vente existante en fonction de son ID, de la nouvelle vente et du nouvel article. " +
                    "La modification est interdite si la vente d'origine est finalisée."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ligne de vente mise à jour avec succès",
                    content = @Content(schema = @Schema(implementation = LigneVenteDTO.class))),
            @ApiResponse(responseCode = "400", description = "Requête invalide", content = @Content),
            @ApiResponse(responseCode = "404", description = "Ligne de vente, article ou vente non trouvé", content = @Content),
            @ApiResponse(responseCode = "403", description = "La vente est finalisée. Modification interdite.", content = @Content)
    })
    @PutMapping("/{ligneVenteId}/vente/{venteId}/article/{articleId}")
    public ResponseEntity<LigneVenteDTO> updateLigneVenteWithVenteAndArticle(
            @Parameter(description = "ID de la ligne de vente") @PathVariable Long ligneVenteId,
            @Parameter(description = "Nouvel ID de la vente") @PathVariable Long venteId,
            @Parameter(description = "Nouvel ID de l'article") @PathVariable Long articleId,
            @Valid @RequestBody LigneVenteDTO ligneVenteDTO) {

        LigneVenteDTO updated = ligneVenteService.updateLigneVente(ligneVenteId, venteId, articleId, ligneVenteDTO);
        return ResponseEntity.ok(updated);
    }

    @Operation(
            summary = "Supprimer une ligne de vente",
            description = "Supprime une ligne de vente en fonction de son identifiant. Cette opération est généralement réservée aux administrateurs."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ligne de vente supprimée avec succès",
                    content = @Content(schema = @Schema(implementation = LigneVenteDTO.class))),
            @ApiResponse(responseCode = "404", description = "Ligne de vente non trouvée", content = @Content),
            @ApiResponse(responseCode = "403", description = "Accès refusé à cette opération", content = @Content)
    })
    @DeleteMapping("/admin/ligneVente/{ligneVenteId}")
    public ResponseEntity<LigneVenteDTO> deleteLigneVente(
            @Parameter(description = "ID de la ligne de vente à supprimer", required = true)
            @PathVariable Long ligneVenteId) {

        LigneVenteDTO deletedLigneVent = ligneVenteService.deleteLigneVente(ligneVenteId);
        return new ResponseEntity<>(deletedLigneVent, HttpStatus.OK);
    }


}
