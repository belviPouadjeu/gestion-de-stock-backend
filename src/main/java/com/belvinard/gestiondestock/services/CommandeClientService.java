package com.belvinard.gestiondestock.services;

import com.belvinard.gestiondestock.dtos.CommandeClientDTO;
import com.belvinard.gestiondestock.dtos.LigneCommandeClientDTO;
import com.belvinard.gestiondestock.models.EtatCommande;

import java.math.BigDecimal;
import java.util.List;

public interface CommandeClientService {

  CommandeClientDTO createCommandeClient(CommandeClientDTO commandeClientDTO);

  CommandeClientDTO updateEtatCommande(Long idCommande, EtatCommande etatCommande);

  CommandeClientDTO updateQuantiteCommande(Long idCommande, Long idLigneCommande, BigDecimal quantite);

  CommandeClientDTO updateClient(Long idCommande, Long idClient);

  CommandeClientDTO updateArticle(Long idCommande, Long idLigneCommande, Long newIdArticle);

  // Delete article ==> delete LigneCommandeClient
  CommandeClientDTO deleteArticle(Long idCommande, Long idLigneCommande);

  CommandeClientDTO findById(Long id);

  CommandeClientDTO findByCode(String code);

  List<CommandeClientDTO> getAllCommandeClients();

  List<LigneCommandeClientDTO> findAllLignesCommandesClientByCommandeClientId(Long idCommande);

  CommandeClientDTO delete(Long id);

}
