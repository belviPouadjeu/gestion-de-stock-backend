package com.belvinard.gestiondestock.services;

import com.belvinard.gestiondestock.dtos.ClientDTO;

import java.util.List;

public interface ClientService {

  ClientDTO createClient(Long entrepriseId, ClientDTO clientDTO);
  
  ClientDTO findByClientId(Long id);

  List<ClientDTO> getAllClients();

  ClientDTO deleteClient(Long id);

}