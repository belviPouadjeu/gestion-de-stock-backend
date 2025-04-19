package com.belvinard.gestiondestock.services;

import com.belvinard.gestiondestock.dtos.CommandeFournisseurDTO;
import com.belvinard.gestiondestock.models.EtatCommande;

import java.util.List;

public interface CommandeFournisseurService {

    // 🔹 Créer une nouvelle commande fournisseur
    CommandeFournisseurDTO save(CommandeFournisseurDTO dto, Long fournisseurId);

    // 🔹 Trouver une commande par ID
    CommandeFournisseurDTO findById(Long id);

    // 🔹 Trouver une commande par code
    CommandeFournisseurDTO findByCode(String code);

    // 🔹 Obtenir toutes les commandes
    List<CommandeFournisseurDTO> findAll();

    // 🔹 Supprimer une commande fournisseur par son ID
    void deleteById(Long id);

    // 🔹 Mettre à jour l'état de la commande (ex: EN_COURS → LIVREE)
    CommandeFournisseurDTO updateEtatCommande(Long idCommande, EtatCommande nouvelEtat);


    // 🔹 Obtenir toutes les commandes liées à un fournisseur donné
    List<CommandeFournisseurDTO> findAllByFournisseurId(Long fournisseurId);
}
