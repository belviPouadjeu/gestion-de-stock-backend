//package com.belvinard.gestiondestock.services;
//
//import com.belvinard.gestiondestock.dtos.LigneCommandeFournisseurDTO;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//public interface LigneCommandeFournisseurService {
//
//    List<LigneCommandeFournisseurDTO> findAllByCommandeFournisseurId(Long idCommande);
//
//    LigneCommandeFournisseurDTO save(LigneCommandeFournisseurDTO dto, Long commandeFournisseurId, Long articleId);
//
//    // 🔹 Obtenir toutes les lignes de commande d'une commande fournisseur
//    List<LigneCommandeFournisseurDTO> findAllLignesCommande(Long idCommande);
//
//    // 🔹 Supprimer une ligne de commande spécifique
//    void deleteLigneCommande(Long idLigneCommande);
//
//    void deleteById(Long idLigneCommande);
//}
