package com.belvinard.gestiondestock.services;

import com.belvinard.gestiondestock.dtos.CommandeFournisseurDTO;
import com.belvinard.gestiondestock.dtos.LigneCommandeFournisseurDTO;
import com.belvinard.gestiondestock.models.EtatCommande;

import java.math.BigDecimal;
import java.util.List;

public interface CommandeFournisseurService {

  CommandeFournisseurDTO createCmdFournisseur(CommandeFournisseurDTO commandeFournisseurDTO);

  CommandeFournisseurDTO updateEtatCommande(Long idCommande, EtatCommande etatCommande);

  CommandeFournisseurDTO updateQuantiteCommande(Long idCommande, Long idLigneCommande, BigDecimal quantite);

  CommandeFournisseurDTO updateFournisseur(Long idCommande, Long idFournisseur);

  CommandeFournisseurDTO updateArticle(Long idCommande, Long idLigneCommande, Long idArticle);

  // Delete article ==> delete LigneCommandeFournisseur
  CommandeFournisseurDTO deleteArticle(Long idCommande, Long idLigneCommande);

  CommandeFournisseurDTO findById(Long id);

  CommandeFournisseurDTO findByCode(String code);

  List<CommandeFournisseurDTO> findAll();

  List<LigneCommandeFournisseurDTO> findAllLignesCommandesFournisseurByCommandeFournisseurId(Long idCommande);

  CommandeFournisseurDTO delete(Long id);

}
