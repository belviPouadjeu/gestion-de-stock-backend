package com.belvinard.gestiondestock.services;

import com.belvinard.gestiondestock.dtos.CommandeClientDTO;
import com.belvinard.gestiondestock.dtos.LigneCommandeClientDTO;
import com.belvinard.gestiondestock.models.EtatCommande;

import java.math.BigDecimal;
import java.util.List;

public interface CommandeClientService {

  CommandeClientDTO createCommandeClient(Long clientId, Long entrepriseId, CommandeClientDTO commandeClientDTO);

  CommandeClientDTO updateEtatCommande(Long idCommande, EtatCommande etatCommande);

  List<CommandeClientDTO> findAll();

  CommandeClientDTO findById(Long id);

  List<LigneCommandeClientDTO> findAllLignesCommandesClientByCommandeClientId(Long idCommande);

  //CommandeClientDTO delete(Long id);

  CommandeClientDTO deleteCommandeClient(Long id);

  CommandeClientDTO findByCode(String code);

}
