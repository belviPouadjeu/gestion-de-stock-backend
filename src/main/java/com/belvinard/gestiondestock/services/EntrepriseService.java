package com.belvinard.gestiondestock.services;

import com.belvinard.gestiondestock.dtos.EntrepriseDTO;
import com.belvinard.gestiondestock.responses.EntrepriseResponse;

public interface EntrepriseService {
    EntrepriseDTO createEntreprise(EntrepriseDTO entrepriseDTO);
    EntrepriseResponse getAllEntreprises();
    EntrepriseDTO findEntrepriseById(Integer id);
    EntrepriseDTO deleteEntreprise(Integer id);
}
