package com.belvinard.gestiondestock.services;

import com.belvinard.gestiondestock.dtos.LigneCommandeFournisseurDTO;

import java.util.List;
import java.util.Optional;

public interface LigneCommandeFournisseurService {

    /**
     * Crée ou met à jour une ligne de commande fournisseur.
     * Vérifie que la commande et l'article existent avant d'enregistrer.
     * Peut aussi recalculer automatiquement le TTC si besoin.
     *
     * @param dto le DTO de la ligne de commande
     * @param commandeFournisseurId l'identifiant de la commande fournisseur
     * @param articleId l'identifiant de l'article
     * @return le DTO sauvegardé ou mis à jour
     */
    LigneCommandeFournisseurDTO save(LigneCommandeFournisseurDTO dto, Long commandeFournisseurId, Long articleId);

    /**
     * Récupère toutes les lignes de commande liées à une commande fournisseur.
     *
     * @param idCommande l'identifiant de la commande
     * @return la liste des lignes de commande correspondantes
     */
    List<LigneCommandeFournisseurDTO> findAllByCommandeId(Long idCommande);

    /**
     * Supprime une ligne de commande spécifique par son identifiant.
     * Vérifie que la commande associée n'est pas livrée avant suppression.
     *
     * @param idLigneCommande l'identifiant de la ligne à supprimer
     * @return le DTO de la ligne supprimée
     * @throws com.belvinard.gestiondestock.exceptions.BusinessRuleException si la commande est livrée
     * @throws com.belvinard.gestiondestock.exceptions.ResourceNotFoundException si la ligne n'existe pas
     */
    LigneCommandeFournisseurDTO deleteById(Long idLigneCommande);

    /**
     * Affiche l'historique des commandes fournisseur pour un article donné.
     *
     * @param idArticle l'identifiant de l'article
     * @return la liste des lignes de commande liées à cet article
     */
    List<LigneCommandeFournisseurDTO> findHistoriqueByArticleId(Long idArticle);

    /**
     * Récupère une ligne de commande fournisseur par son identifiant.
     *
     * @param idLigneCommande l'identifiant de la ligne
     * @return le DTO s'il existe
     */
    Optional<LigneCommandeFournisseurDTO> findById(Long idLigneCommande);

}
