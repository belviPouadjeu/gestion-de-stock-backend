package com.belvinard.gestiondestock.services;

import com.belvinard.gestiondestock.dtos.VenteDTO;
import com.belvinard.gestiondestock.responses.VenteResponse;

public interface VenteService {
    VenteResponse getAllVentes();
    VenteDTO  createVente(Long entrepriseId, VenteDTO venteDTO);
    VenteDTO findVenteById(Long id);
    VenteDTO deleteVente(Long id);
}
