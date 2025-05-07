package com.belvinard.gestiondestock.services.impl;

import com.belvinard.gestiondestock.dtos.VenteDTO;
import com.belvinard.gestiondestock.exceptions.APIException;
import com.belvinard.gestiondestock.exceptions.ResourceNotFoundException;
import com.belvinard.gestiondestock.models.Entreprise;
import com.belvinard.gestiondestock.models.Vente;
import com.belvinard.gestiondestock.repositories.EntrepriseRepository;
import com.belvinard.gestiondestock.repositories.VentesRepository;
import com.belvinard.gestiondestock.responses.VenteResponse;
import com.belvinard.gestiondestock.services.VenteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VenteServiceImpl implements VenteService {

    @Autowired
    private VentesRepository ventesRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private EntrepriseRepository entrepriseRepository;

    public VenteServiceImpl(VentesRepository ventesRepository, ModelMapper modelMapper,
                            EntrepriseRepository entrepriseRepository) {
        this.ventesRepository = ventesRepository;
        this.modelMapper = modelMapper;
        this.entrepriseRepository = entrepriseRepository;
    }

    @Override
    public VenteResponse getAllVentes() {
       List<Vente> ventes = ventesRepository.findAll();
        if (ventes.isEmpty()){
            throw new APIException("No ventes created until now !");
        }

        List<VenteDTO> venteDTOS = ventes.stream()
                .map(vente -> modelMapper.map(vente, VenteDTO.class))
                .toList();
        VenteResponse venteResponse = new VenteResponse();
        venteResponse.setContent(venteDTOS);

        return venteResponse;
    }

    @Override
    public VenteDTO createVente(Long entrepriseId, VenteDTO venteDTO) {
        // Convert DTO to Entity
        Vente vente = modelMapper.map(venteDTO, Vente.class);
        //vente.setIdEntreprise(entrepriseId);

        // Check if vente with the same name already exists to the database
        Vente venteFromDb = ventesRepository.findByCode(vente.getCode());
        if (venteFromDb != null){
            throw new APIException("Vente with this code " + vente.getCode() + " already exist !");
        }

        // Récupération de l'entreprise par son ID
        Entreprise entreprise = entrepriseRepository.findById(entrepriseId)
                .orElseThrow(() -> new ResourceNotFoundException("Entreprise non trouvee avec l'Id " + entrepriseId));

        vente.setEntreprise(entreprise);

        // Save the new Vent to the database
        Vente savedVente = ventesRepository.save(vente);

        // Convert back to DTO
        return modelMapper.map(savedVente, VenteDTO.class);
    }

    @Override
    public VenteDTO findVenteById(Long id) {
        // Retrieve the vente from the database from it ID
        Vente vente = ventesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vente", "venteId", id));

        // Convert the Vente entity to a VenteDTO and return it
        return modelMapper.map(vente, VenteDTO.class);

    }

    @Override
    public VenteDTO deleteVente(Long id) {
        // Retrieve the vente from the database from it ID
        Vente vente = ventesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vente", "venteId", id));

        // Delete the found vente from the database
        ventesRepository.delete(vente);

        // Convert the deleted vente to a VenteDTO and retrun it
        return modelMapper.map(vente, VenteDTO.class);

    }

}
