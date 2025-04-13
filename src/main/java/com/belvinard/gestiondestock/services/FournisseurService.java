package com.belvinard.gestiondestock.services;

import com.belvinard.gestiondestock.dtos.FournisseurDTO;

import java.util.List;

public interface FournisseurService {

  FournisseurDTO createFournisseur(FournisseurDTO fournisseurDTO);

  FournisseurDTO findById(Long id);

  List<FournisseurDTO> findAll();

  FournisseurDTO delete(Long id);

}