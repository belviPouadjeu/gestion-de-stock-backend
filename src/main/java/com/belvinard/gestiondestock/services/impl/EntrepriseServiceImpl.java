package com.belvinard.gestiondestock.services.impl;

import com.belvinard.gestiondestock.dtos.EntrepriseDTO;
import com.belvinard.gestiondestock.exceptions.APIException;
import com.belvinard.gestiondestock.exceptions.ResourceNotFoundException;
import com.belvinard.gestiondestock.models.Entreprise;
import com.belvinard.gestiondestock.repositories.EntepriseRepository;
import com.belvinard.gestiondestock.responses.EntrepriseResponse;
import com.belvinard.gestiondestock.services.EntrepriseService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EntrepriseServiceImpl implements EntrepriseService {
    private final EntepriseRepository entepriseRepository;
    private final ModelMapper modelMapper;

    public EntrepriseServiceImpl(EntepriseRepository entepriseRepository, ModelMapper modelMapper) {
        this.entepriseRepository = entepriseRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public EntrepriseDTO createEntreprise(EntrepriseDTO entrepriseDTO) {
        // Convertir EntrepriseDTO en Entreprise
        Entreprise entreprise = modelMapper.map(entrepriseDTO, Entreprise.class);

        Entreprise entrepriseFromDb = entepriseRepository.findByNom(entreprise.getNom());
        if (entrepriseFromDb != null) {
            throw new ResourceNotFoundException("Entreprise with the name " + entreprise.getNom() + " already exist !!");
        }
        // Enregistrer la nouvelle entreprise dans la base de donnee
        Entreprise savedEntreprise = entepriseRepository.save(entreprise);

        // Convertir la nouvelle entreprise en EntrepriseDTO
        return modelMapper.map(savedEntreprise, EntrepriseDTO.class);

    }

    @Override
    public EntrepriseResponse getAllEntreprises() {
        List<Entreprise> entreprises = entepriseRepository.findAll();
        if (entreprises.isEmpty()) {
            throw new APIException("No Entreprises created untill now !!!");
        }

        // Convert the list of Entreprise entities to a list of EntrepriseDTO objects using ModelMapper
        List<EntrepriseDTO> entrepriseDTOS = entreprises.stream()
                .map(entreprise -> modelMapper.map(entreprise, EntrepriseDTO.class))
                .toList();

        EntrepriseResponse entrepriseResponse = new EntrepriseResponse();
        entrepriseResponse.setContent(entrepriseDTOS);
        return entrepriseResponse;
    }

    @Override
    public EntrepriseDTO findEntrepriseById(Integer id) {
        Entreprise entreprise = entepriseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entreprise with id " + id + " not found !!"));
        return modelMapper.map(entreprise, EntrepriseDTO.class);
    }

    @Override
    public EntrepriseDTO deleteEntreprise(Integer id) {
        Entreprise entreprise = entepriseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entreprise with id " + id + " not found !!"));
        // Convert to DTO before deletion for return
        EntrepriseDTO entrepriseDTO = modelMapper.map(entreprise, EntrepriseDTO.class);

        entepriseRepository.delete(entreprise);
        return entrepriseDTO; // Return DTO with deleted details
    }


}