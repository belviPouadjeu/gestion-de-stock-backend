package com.belvinard.gestiondestock.services;

import com.belvinard.gestiondestock.dtos.ClientDTO;

import java.util.List;

public interface ClientService {

  ClientDTO createClient(ClientDTO clientDTO);

  ClientDTO findById(Long id);

  List<ClientDTO> getAllClients();

  ClientDTO delete(Long id);

}