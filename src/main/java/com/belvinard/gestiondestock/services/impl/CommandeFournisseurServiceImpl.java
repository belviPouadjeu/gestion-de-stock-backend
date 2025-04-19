package com.belvinard.gestiondestock.services.impl;

import com.belvinard.gestiondestock.dtos.CommandeFournisseurDTO;
import com.belvinard.gestiondestock.exceptions.APIException;
import com.belvinard.gestiondestock.exceptions.InvalidEntityException;
import com.belvinard.gestiondestock.exceptions.ResourceNotFoundException;
import com.belvinard.gestiondestock.models.CommandeFournisseur;
import com.belvinard.gestiondestock.models.Entreprise;
import com.belvinard.gestiondestock.models.EtatCommande;
import com.belvinard.gestiondestock.models.Fournisseur;
import com.belvinard.gestiondestock.repositories.CommandeFournisseurRepository;
import com.belvinard.gestiondestock.repositories.EntrepriseRepository;
import com.belvinard.gestiondestock.repositories.FournisseurRepository;
import com.belvinard.gestiondestock.services.CommandeFournisseurService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommandeFournisseurServiceImpl implements CommandeFournisseurService {

    private final CommandeFournisseurRepository commandeFournisseurRepository;
    private final FournisseurRepository fournisseurRepository;
    //private final EntrepriseRepository entrepriseRepository;
    private final ModelMapper modelMapper;

    public CommandeFournisseurServiceImpl(
        CommandeFournisseurRepository commandeFournisseurRepository,
        FournisseurRepository fournisseurRepository,
        //EntrepriseRepository entrepriseRepository,
        ModelMapper modelMapper
    ) {
        this.commandeFournisseurRepository = commandeFournisseurRepository;
        this.fournisseurRepository = fournisseurRepository;
        //this.entrepriseRepository = entrepriseRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public CommandeFournisseurDTO save(CommandeFournisseurDTO dto, Long fournisseurId) {
        // Vérification des paramètres
        if (dto == null || dto.getCode() == null || dto.getCode().isBlank()) {
            throw new InvalidEntityException("Commande fournisseur invalide : le code est requis");
        }

        // Vérification de l'unicité du code
        boolean codeExists = commandeFournisseurRepository.existsByCode(dto.getCode());
        if (codeExists) {
            throw new APIException("Une commande fournisseur existe déjà avec le code : " + dto.getCode());
        }

        // Récupération du fournisseur par son ID
        Fournisseur fournisseur = fournisseurRepository.findById(fournisseurId)
                .orElseThrow(() -> new ResourceNotFoundException("Fournisseur non trouvé avec l'ID: " + fournisseurId));

        // Création de l'entité CommandeFournisseur à partir du DTO
        CommandeFournisseur commande = modelMapper.map(dto, CommandeFournisseur.class);
        commande.setFournisseur(fournisseur);

        // Sauvegarde de la commande fournisseur dans la base de données
        CommandeFournisseur saved = commandeFournisseurRepository.save(commande);

        // Retourner le DTO de la commande sauvegardée
        return modelMapper.map(saved, CommandeFournisseurDTO.class);
    }


    @Override
    public CommandeFournisseurDTO findById(Long id) {
        // Vérification de l'existence de la commande
        CommandeFournisseur commande = commandeFournisseurRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Commande fournisseur non trouvée avec l'ID: " + id));

        // Retourner le DTO de la commande
        return modelMapper.map(commande, CommandeFournisseurDTO.class);
    }

    @Override
    public CommandeFournisseurDTO findByCode(String code) {
        // Vérification de l'existence de la commande par code
        CommandeFournisseur commande = commandeFournisseurRepository.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundException("Commande fournisseur non trouvée avec le code: " + code));

        // Retourner le DTO de la commande
        return modelMapper.map(commande, CommandeFournisseurDTO.class);
    }

    @Override
    public List<CommandeFournisseurDTO> findAll() {
        // Récupérer toutes les commandes
        List<CommandeFournisseur> commandes = commandeFournisseurRepository.findAll();

        // Convertir la liste des commandes en une liste de DTO
        return commandes.stream()
                .map(commande -> modelMapper.map(commande, CommandeFournisseurDTO.class))
                .collect(Collectors.toList());
    }


    @Override
    public void deleteById(Long id) {
        // Vérifier si la commande existe
        CommandeFournisseur commande = commandeFournisseurRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Commande fournisseur non trouvée avec l'ID: " + id));

        // Supprimer la commande de la base de données
        commandeFournisseurRepository.delete(commande);
    }

    @Override
    public CommandeFournisseurDTO updateEtatCommande(Long idCommande, EtatCommande nouvelEtat) {
        // Récupérer la commande
        CommandeFournisseur commande = commandeFournisseurRepository.findById(idCommande)
                .orElseThrow(() -> new EntityNotFoundException("Commande fournisseur non trouvée avec l'ID: " + idCommande));

        // Modifier l'état de la commande
        commande.setEtatCommande(nouvelEtat);

        // Sauvegarder la commande mise à jour
        CommandeFournisseur updatedCommande = commandeFournisseurRepository.save(commande);

        // Retourner le DTO de la commande mise à jour
        return modelMapper.map(updatedCommande, CommandeFournisseurDTO.class);
    }

    @Override
    public List<CommandeFournisseurDTO> findAllByFournisseurId(Long fournisseurId) {
        if (!fournisseurRepository.existsById(fournisseurId)) {
            throw new ResourceNotFoundException("Fournisseur non trouvé avec l'ID: " + fournisseurId);
        }

        List<CommandeFournisseur> commandes = commandeFournisseurRepository.findAllByFournisseurId(fournisseurId);

        return commandes.stream()
                .map(commande -> modelMapper.map(commande, CommandeFournisseurDTO.class))
                .collect(Collectors.toList());
    }









}
