package com.belvinard.gestiondestock.services;

import com.belvinard.gestiondestock.dtos.LigneVenteDTO;
import com.belvinard.gestiondestock.responses.LigneVentResponse;

import java.util.List;

public interface LigneVenteService {

    LigneVentResponse getAllLigneVente();

   LigneVenteDTO createLigneVente(LigneVenteDTO ligneVenteDTO, Long venteId, Long ArticleId);

    LigneVentResponse findAllByVenteId(Long venteId);

    LigneVenteDTO updateLigneVente(Long ligneVenteId, Long venteId, Long articleId, LigneVenteDTO dto);
}






//package com.belvinard.gestiondestock.services;
//
//import com.belvinard.gestiondestock.dtos.LigneVenteDTO;
//
//import java.util.List;
//
//public interface LigneVenteService {
//
//  LigneVenteDTO createLigneVente(LigneVenteDTO ligneVenteDTO);
//
//  LigneVenteDTO findById(Long id);
//
//  List<LigneVenteDTO> getAllLignesVente();
//
//  List<LigneVenteDTO> findAllByVenteId(Long venteId); // 👈 Pour afficher les lignes liées à une vente
//
//  LigneVenteDTO delete(Long id);
//
//  LigneVenteDTO updateLigneVente(Long id, LigneVenteDTO ligneVenteDTO);
//
//
//}
