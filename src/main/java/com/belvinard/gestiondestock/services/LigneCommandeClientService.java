package com.belvinard.gestiondestock.services;

import com.belvinard.gestiondestock.dtos.LigneCommandeClientDTO;

import java.util.List;

public interface LigneCommandeClientService {

    //LigneCommandeClientDTO create(Long commandeId, Long articleId, LigneCommandeClientDTO ligneDTO);

    LigneCommandeClientDTO createLigneCommandeClient(Long commandeId, Long articleId, LigneCommandeClientDTO ligneDTO);

    List<LigneCommandeClientDTO> saveAll(Long commandeId, List<LigneCommandeClientDTO> lignesDTO);

    LigneCommandeClientDTO update(Long ligneId, LigneCommandeClientDTO ligneDTO);

    LigneCommandeClientDTO delete(Long ligneId);

    LigneCommandeClientDTO findById(Long idLigne);

    List<LigneCommandeClientDTO> findAll();

}
