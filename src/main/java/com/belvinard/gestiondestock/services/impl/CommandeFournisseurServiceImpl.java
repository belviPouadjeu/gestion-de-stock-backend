package com.belvinard.gestiondestock.services.impl;

import com.belvinard.gestiondestock.dtos.CommandeFournisseurDTO;
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
    public CommandeFournisseurDTO save(CommandeFournisseurDTO dto, Long fournisseurId) {
        // Vérification des paramètres
        if (dto == null) {
            throw new InvalidEntityException("Commande fournisseur invalide");
        }

        // Récupération du fournisseur par son ID
        Fournisseur fournisseur = fournisseurRepository.findById(fournisseurId)
                .orElseThrow(() -> new ResourceNotFoundException("Fournisseur non trouvé avec l'ID: " + fournisseurId));

        // Création de l'entité CommandeFournisseur à partir du DTO
        CommandeFournisseur commande = modelMapper.map(dto, CommandeFournisseur.class);
        commande.setFournisseur(fournisseur);
        //commande.setEntreprise(entreprise);

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
        return List.of();
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
        // Trouver toutes les commandes pour un fournisseur donné
        List<CommandeFournisseur> commandes = commandeFournisseurRepository.findAllByFournisseurId(fournisseurId);

        // Convertir les entités en DTO
        return commandes.stream()
                .map(commande -> modelMapper.map(commande, CommandeFournisseurDTO.class))
                .collect(Collectors.toList());
    }








}
