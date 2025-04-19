package com.belvinard.gestiondestock.services.impl;

import com.belvinard.gestiondestock.dtos.EntrepriseDTO;
import com.belvinard.gestiondestock.dtos.FournisseurDTO;
import com.belvinard.gestiondestock.exceptions.APIException;
import com.belvinard.gestiondestock.exceptions.ResourceNotFoundException;
import com.belvinard.gestiondestock.models.Entreprise;
import com.belvinard.gestiondestock.models.Fournisseur;
import com.belvinard.gestiondestock.repositories.EntrepriseRepository;
import com.belvinard.gestiondestock.repositories.FournisseurRepository;
import com.belvinard.gestiondestock.services.FournisseurService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FournisseurServiceImpl implements FournisseurService {

    private final FournisseurRepository fournisseurRepository;
    private final EntrepriseRepository entrepriseRepository;
    private final ModelMapper modelMapper;

    public FournisseurServiceImpl(
        FournisseurRepository fournisseurRepository,
        EntrepriseRepository entrepriseRepository,
        ModelMapper modelMapper
    ) {
        this.fournisseurRepository = fournisseurRepository;
        this.entrepriseRepository = entrepriseRepository;
        this.modelMapper = modelMapper;
    }

    // ✅ Création d'un fournisseur
    @Override
    public FournisseurDTO createFournisseur(Long entrepriseId, FournisseurDTO fournisseurDTO) {
        // 🔎 Récupérer l'entreprise
        Entreprise entreprise = entrepriseRepository.findById(entrepriseId)
                .orElseThrow(() -> new ResourceNotFoundException("Entreprise non trouvée avec l'id " + entrepriseId));

        // 🔄 Mapper le DTO vers l'entité
        Fournisseur fournisseur = modelMapper.map(fournisseurDTO, Fournisseur.class);

        // 🔗 Lier à l'entreprise
        fournisseur.setEntreprise(entreprise);

        // 💾 Enregistrer le fournisseur
        Fournisseur saved = fournisseurRepository.save(fournisseur);

        // 🔁 Remapper en DTO et ajouter entrepriseId + entrepriseDetails manuellement
        FournisseurDTO savedDTO = modelMapper.map(saved, FournisseurDTO.class);
        savedDTO.setEntrepriseId(entreprise.getId());
        savedDTO.setEntrepriseDetails(modelMapper.map(entreprise, EntrepriseDTO.class));

        return savedDTO;
    }



    // ✅ Trouver un fournisseur par ID
    @Override
    public FournisseurDTO findById(Long id) {
        Fournisseur fournisseur = fournisseurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fournisseur non trouvé avec l'id " + id));

        return modelMapper.map(fournisseur, FournisseurDTO.class);
    }

    // ✅ Récupérer tous les fournisseurs
    @Override
    public List<FournisseurDTO> findAll() {
        List<Fournisseur> fournisseurs = fournisseurRepository.findAll();

        if (fournisseurs.isEmpty()) {
            throw new APIException("Aucun fournisseur trouvé dans la base de données");
        }

        return fournisseurs.stream()
                .map(f -> modelMapper.map(f, FournisseurDTO.class))
                .collect(Collectors.toList());
    }


    // ✅ Supprimer un fournisseur
    @Override
    public FournisseurDTO delete(Long id) {
        Fournisseur fournisseur = fournisseurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fournisseur non trouvé avec l'id " + id));

        fournisseurRepository.delete(fournisseur);
        return modelMapper.map(fournisseur, FournisseurDTO.class);
    }
}
