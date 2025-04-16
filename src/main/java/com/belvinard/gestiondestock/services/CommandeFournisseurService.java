package com.belvinard.gestiondestock.services;

import com.belvinard.gestiondestock.dtos.CommandeFournisseurDTO;
import com.belvinard.gestiondestock.dtos.LigneCommandeFournisseurDTO;
import com.belvinard.gestiondestock.models.EtatCommande;

import java.math.BigDecimal;
import java.util.List;

public interface CommandeFournisseurService {

  CommandeFournisseurDTO create(CommandeFournisseurDTO commandeFournisseurDTO);

  CommandeFournisseurDTO findById(Long id);

  CommandeFournisseurDTO findByCode(String code);

  List<CommandeFournisseurDTO> findAll();

  List<LigneCommandeFournisseurDTO> findAllLignesByCommandeId(Long idCommande);

  CommandeFournisseurDTO delete(Long id);
}
