package com.belvinard.gestiondestock.services;

import com.belvinard.gestiondestock.dtos.VentesDTO;

import java.util.List;

public interface VentesService {

  VentesDTO createVenteService(VentesDTO ventesDTO);

  VentesDTO findById(Long id);

  VentesDTO findByCode(String code);

  List<VentesDTO> getAllVente();

  VentesDTO delete(Long id);

}
