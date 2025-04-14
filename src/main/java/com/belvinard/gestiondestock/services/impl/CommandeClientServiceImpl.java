package com.belvinard.gestiondestock.services.impl;

import com.belvinard.gestiondestock.dtos.ClientDTO;
import com.belvinard.gestiondestock.dtos.CommandeClientDTO;
import com.belvinard.gestiondestock.dtos.EntrepriseDTO;
import com.belvinard.gestiondestock.dtos.LigneCommandeClientDTO;
import com.belvinard.gestiondestock.exceptions.ResourceNotFoundException;
import com.belvinard.gestiondestock.models.*;
import com.belvinard.gestiondestock.repositories.ClientRepository;
import com.belvinard.gestiondestock.repositories.CommandeClientRepository;
import com.belvinard.gestiondestock.repositories.EntrepriseRepository;
import com.belvinard.gestiondestock.repositories.LigneCommandeClientRepository;
import com.belvinard.gestiondestock.services.CommandeClientService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommandeClientServiceImpl implements CommandeClientService {

    private final CommandeClientRepository commandeClientRepository;
    private final EntrepriseRepository entrepriseRepository;
    private final ClientRepository clientRepository;
    private final LigneCommandeClientRepository ligneCommandeClientRepository;
    private final ModelMapper modelMapper;
    /**
     * Crée une nouvelle commande client pour un client donné.
     *
     * @param clientId L'identifiant du client associé à la commande.
     * @param commandeClientDTO Les données de la commande à créer.
     * @return Le DTO représentant la commande client créée.
     * @throws ResourceNotFoundException si le client ou l'entreprise n'existent pas.
     */
    @Override
    public CommandeClientDTO createCommandeClient(Long clientId, Long entrepriseId, CommandeClientDTO commandeClientDTO) {
        // Vérification de l'existence du client
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client", "id", clientId));

        // Vérification de l'existence de l'entreprise associée
        Entreprise entreprise = entrepriseRepository.findById(commandeClientDTO.getEntrepriseId())
                .orElseThrow(() -> new ResourceNotFoundException("Entreprise", "id", commandeClientDTO.getEntrepriseId()));

        // Mapping du DTO vers l'entité CommandeClient
        CommandeClient commandeClient = modelMapper.map(commandeClientDTO, CommandeClient.class);

        // Association du client et de l'entreprise à la commande
        commandeClient.setClient(client);
        commandeClient.setEntreprise(entreprise);

        // Sauvegarde en base
        CommandeClient savedCommande = commandeClientRepository.save(commandeClient);

        // Mapping inverse : entité persistée vers DTO
        CommandeClientDTO savedDTO = modelMapper.map(savedCommande, CommandeClientDTO.class);

        // Ajout manuel du client (clientDetails) si nécessaire pour le front
        savedDTO.setClientDetails(modelMapper.map(client, ClientDTO.class));
        savedDTO.setEntrepriseId(entreprise.getId());

        return savedDTO;
    }

    /**
     * Met à jour l'état d'une commande client.
     *
     * @param idCommande  ID de la commande à modifier.
     * @param etatCommande Nouvel état à appliquer à la commande.
     * @return Le DTO de la commande mise à jour.
     * @throws ResourceNotFoundException si la commande avec l'ID donné n'existe pas.
     */
    @Override
    public CommandeClientDTO updateEtatCommande(Long idCommande, EtatCommande etatCommande) {

        // 1. Vérifier l'existence de la commande
        CommandeClient commande = commandeClientRepository.findById(idCommande)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "CommandeClient", "id", idCommande
                ));

        // 2. Mettre à jour l'état de la commande
        commande.setEtatCommande(etatCommande);

        // 3. Sauvegarder la commande modifiée
        CommandeClient updatedCommande = commandeClientRepository.save(commande);

        // 4. Mapper vers le DTO
        CommandeClientDTO dto = modelMapper.map(updatedCommande, CommandeClientDTO.class);
        dto.setClientId(commande.getClient().getId());
        dto.setEntrepriseId(commande.getEntreprise().getId());

        // Bonus : Remplir les détails du client
        ClientDTO clientDTO = modelMapper.map(commande.getClient(), ClientDTO.class);
        dto.setClientDetails(clientDTO);

        return dto;
    }

    /**
     * Récupère toutes les commandes clients enregistrées dans la base de données.
     *
     * @return Une liste de toutes les commandes clients sous forme de DTOs.
     */
    @Override
    public List<CommandeClientDTO> findAll() {
        return commandeClientRepository.findAll().stream()
                .map(commandeClient -> {
                    CommandeClientDTO dto = modelMapper.map(commandeClient, CommandeClientDTO.class);

                    // IDs de base
                    dto.setClientId(commandeClient.getClient().getId());
                    dto.setEntrepriseId(commandeClient.getEntreprise().getId());

                    // Client détaillé
                    dto.setClientDetails(modelMapper.map(commandeClient.getClient(), ClientDTO.class));

                    // Entreprise détaillée
                    dto.setEntrepriseDetails(modelMapper.map(commandeClient.getEntreprise(), EntrepriseDTO.class));

                    // Lignes de commande client détaillées
                    if (commandeClient.getLigneCommandeClients() != null) {
                        List<LigneCommandeClientDTO> lignesDTO = commandeClient.getLigneCommandeClients()
                                .stream()
                                .map(ligne -> modelMapper.map(ligne, LigneCommandeClientDTO.class))
                                .collect(Collectors.toList());
                        dto.setLigneCommandeClients(lignesDTO);
                    }

                    return dto;
                })
                .collect(Collectors.toList());
    }


    /**
     * Récupère une commande client spécifique à partir de son identifiant.
     *
     * @param id L'identifiant de la commande client à rechercher.
     * @return Le DTO de la commande client si elle est trouvée.
     * @throws ResourceNotFoundException si aucune commande client n'est trouvée avec l'identifiant donné.
     */
    @Override
    public CommandeClientDTO findById(Long id) {
        CommandeClient commandeClient = commandeClientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Aucune commande client trouvée avec l'ID : " + id
                ));

        CommandeClientDTO dto = modelMapper.map(commandeClient, CommandeClientDTO.class);

        // IDs
        dto.setClientId(commandeClient.getClient().getId());
        dto.setEntrepriseId(commandeClient.getEntreprise().getId());

        // Détails du client
        dto.setClientDetails(modelMapper.map(commandeClient.getClient(), ClientDTO.class));

        // Détails de l'entreprise
        dto.setEntrepriseDetails(modelMapper.map(commandeClient.getEntreprise(), EntrepriseDTO.class));

        // Détails des lignes de commande
        if (commandeClient.getLigneCommandeClients() != null) {
            List<LigneCommandeClientDTO> lignesDTO = commandeClient.getLigneCommandeClients()
                    .stream()
                    .map(ligne -> modelMapper.map(ligne, LigneCommandeClientDTO.class))
                    .collect(Collectors.toList());
            dto.setLigneCommandeClients(lignesDTO);
        }

        return dto;
    }


    @Override
    public List<LigneCommandeClientDTO> findAllLignesCommandesClientByCommandeClientId(Long idCommande) {
        // Vérifie que la commande existe
        CommandeClient commande = commandeClientRepository.findById(idCommande)
                .orElseThrow(() -> new ResourceNotFoundException("Commande client introuvable avec l'ID " + idCommande));

        // Récupère toutes les lignes associées à la commande
        List<LigneCommandeClient> lignes = ligneCommandeClientRepository.findAllByCommandeClientId(idCommande);

        // Transforme chaque ligne en DTO
        return lignes.stream()
                .map(ligne -> modelMapper.map(ligne, LigneCommandeClientDTO.class))
                .collect(Collectors.toList());
    }


    @Override
    public CommandeClientDTO deleteCommandeClient(Long id) {
        CommandeClient commande = commandeClientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commande client introuvable avec l'ID " + id));

        commandeClientRepository.delete(commande);

        return modelMapper.map(commande, CommandeClientDTO.class);
    }

    @Override
    public CommandeClientDTO findByCode(String code) {
        return null;
    }


}
