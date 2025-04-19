package com.belvinard.gestiondestock.controllers;

import com.belvinard.gestiondestock.dtos.LigneCommandeFournisseurDTO;
import com.belvinard.gestiondestock.services.LigneCommandeFournisseurService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lignes-commande-fournisseur")
@RequiredArgsConstructor
@Tag(name = "Lignes Commande Fournisseur", description = "Gestion des lignes de commande fournisseur")
public class LigneCommandeFournisseurController {

    private final LigneCommandeFournisseurService ligneCommandeFournisseurService;

    /**
     * 🔹 Crée une nouvelle ligne de commande fournisseur
     */
    @PostMapping("/save")
    @Operation(summary = "Créer ou mettre à jour une ligne", description = "Permet d'ajouter ou modifier une ligne de commande fournisseur en précisant l'article et la commande")
    public ResponseEntity<LigneCommandeFournisseurDTO> save(
            @RequestBody LigneCommandeFournisseurDTO dto,
            @RequestParam Long commandeId,
            @RequestParam Long articleId
    ) {
        return ResponseEntity.ok(ligneCommandeFournisseurService.save(dto, commandeId, articleId));
    }

    /**
     * 🔹 Récupère toutes les lignes associées à une commande fournisseur
     */
    @GetMapping("/commande/{idCommande}")
    @Operation(summary = "Lister les lignes d'une commande", description = "Renvoie toutes les lignes associées à une commande fournisseur donnée")
    public ResponseEntity<List<LigneCommandeFournisseurDTO>> findAllByCommandeId(@PathVariable Long idCommande) {
        return ResponseEntity.ok(ligneCommandeFournisseurService.findAllByCommandeId(idCommande));
    }

    /**
     * 🔹 Supprime une ligne de commande fournisseur
     */
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Supprimer une ligne", description = "Permet de supprimer une ligne de commande fournisseur si la commande n'est pas encore livrée")
    public ResponseEntity<LigneCommandeFournisseurDTO> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok(ligneCommandeFournisseurService.deleteById(id));
    }

    /**
     * 🔹 Récupère l'historique de commandes fournisseur pour un article donné
     */
    @GetMapping("/historique/article/{idArticle}")
    @Operation(summary = "Historique d'un article", description = "Liste toutes les lignes de commandes fournisseur où cet article a été commandé")
    public ResponseEntity<List<LigneCommandeFournisseurDTO>> findHistoriqueByArticleId(@PathVariable Long idArticle) {
        return ResponseEntity.ok(ligneCommandeFournisseurService.findHistoriqueByArticleId(idArticle));
    }

    /**
     * 🔹 Récupère une ligne de commande fournisseur spécifique
     */
    @GetMapping("/{id}")
    @Operation(summary = "Chercher une ligne", description = "Permet de récupérer une ligne de commande fournisseur par son identifiant")
    public ResponseEntity<LigneCommandeFournisseurDTO> findById(@PathVariable Long id) {
        return ligneCommandeFournisseurService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
