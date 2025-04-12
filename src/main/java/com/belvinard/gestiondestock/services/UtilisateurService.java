package com.belvinard.gestiondestock.services;

import com.belvinard.gestiondestock.dtos.UtilisateurDTO;

import java.util.List;

public interface UtilisateurService {

  UtilisateurDTO createUtilisateur(UtilisateurDTO utilisateurDTO);

  UtilisateurDTO findById(Long id);

  List<UtilisateurDTO> findAll();

  UtilisateurDTO delete(Long id);

  UtilisateurDTO findByEmail(String email);

  //UtilisateurDTO changerMotDePasse(ChangerMotDePasseUtilisateurDTO utilisateurDTO);


}