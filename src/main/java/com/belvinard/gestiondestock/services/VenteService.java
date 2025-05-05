package com.belvinard.gestiondestock.services;

import com.belvinard.gestiondestock.dtos.VenteDTO;
import com.belvinard.gestiondestock.models.Vente;
import com.belvinard.gestiondestock.responses.VenteResponse;

import java.util.List;

public interface VenteService {
    VenteResponse getAllVentes();
    VenteDTO  createVente(Long entrepriseId, VenteDTO venteDTO);
}
