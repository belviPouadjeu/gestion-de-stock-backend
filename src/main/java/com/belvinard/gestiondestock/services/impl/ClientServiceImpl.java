package com.belvinard.gestiondestock.services.impl;

import com.belvinard.gestiondestock.dtos.ClientDTO;
import com.belvinard.gestiondestock.exceptions.ResourceNotFoundException;
import com.belvinard.gestiondestock.models.Client;
import com.belvinard.gestiondestock.models.Entreprise;
import com.belvinard.gestiondestock.repositories.ClientRepository;
import com.belvinard.gestiondestock.repositories.EntrepriseRepository;
import com.belvinard.gestiondestock.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final EntrepriseRepository entrepriseRepository;
    private final ModelMapper modelMapper;

    /**
     * Crée un nouveau client dans la base de données, lié à une entreprise donnée.
     *
     * @param entrepriseId ID de l'entreprise à laquelle le client appartient.
     * @param clientDTO    DTO contenant les informations du client à créer.
     * @return Le DTO du client nouvellement créé.
     * @throws ResourceNotFoundException si l'entreprise n'existe pas.
     */
    @Override
    public ClientDTO createClient(Long entrepriseId, ClientDTO clientDTO) {
        // Vérification que l'entreprise existe
        Entreprise entreprise = entrepriseRepository.findById(entrepriseId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Entreprise", "id", entrepriseId
                ));

        // Mapping DTO vers entité
        Client client = modelMapper.map(clientDTO, Client.class);

        // Affecter l'entreprise récupérée
        client.setEntreprise(entreprise);

        // Sauvegarde
        Client savedClient = clientRepository.save(client);

        // Mapping inverse vers DTO
        ClientDTO savedDTO = modelMapper.map(savedClient, ClientDTO.class);
        savedDTO.setEntrepriseId(entrepriseId);

        return savedDTO;
    }


    /**
     * Recherche un client par son identifiant.
     *
     * @param id Identifiant du client à rechercher.
     * @return Le DTO du client trouvé.
     * @throws ResourceNotFoundException si aucun client n'existe avec cet identifiant.
     */
    @Override
    public ClientDTO findByClientId(Long id) {
        // Récupération du client à partir de la base de données
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Client", "id", id
                ));

        // Conversion de l'entité Client en DTO
        ClientDTO clientDTO = modelMapper.map(client, ClientDTO.class);

        // Ajout explicite de l'id de l'entreprise associée
        if (client.getEntreprise() != null) {
            clientDTO.setEntrepriseId(client.getEntreprise().getId());
        }

        return clientDTO;
    }

    /**
     * Récupère la liste de tous les clients présents en base de données.
     *
     * @return Liste des objets ClientDTO correspondant aux clients enregistrés.
     */
    @Override
    public List<ClientDTO> getAllClients() {
        // Récupération de tous les clients depuis la base de données
        List<Client> clients = clientRepository.findAll();

        // Transformation de la liste d'entités Client en liste de DTOs
        return clients.stream()
                .map(client -> {
                    ClientDTO dto = modelMapper.map(client, ClientDTO.class);

                    // On ajoute manuellement l'entrepriseId pour garder la relation explicite
                    if (client.getEntreprise() != null) {
                        dto.setEntrepriseId(client.getEntreprise().getId());
                    }

                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Supprime un client en fonction de son identifiant.
     * Si le client est lié à une commande client, une exception est levée pour protéger l'intégrité des données.
     *
     * @param id Identifiant du client à supprimer
     * @return Le ClientDTO correspondant au client supprimé
     * @throws ResourceNotFoundException si aucun client n’est trouvé avec cet ID
     * @throws IllegalStateException si le client est lié à une ou plusieurs commandes
     */
    @Override
    public ClientDTO deleteClient(Long id) {
        // Étape 1 : Vérification de l'existence du client
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aucun client trouvé avec l'ID : " + id));

        // Étape 2 : Vérification d'intégrité référentielle (commandes liées)
        if (client.getCommandeClients() != null && !client.getCommandeClients().isEmpty()) {
            throw new IllegalStateException("Impossible de supprimer ce client car il est lié à des commandes.");
        }

        // Étape 3 : Suppression du client
        clientRepository.delete(client);

        // Étape 4 : Retourner le DTO du client supprimé (utile si on veut afficher une confirmation)
        ClientDTO deletedClientDTO = modelMapper.map(client, ClientDTO.class);
        if (client.getEntreprise() != null) {
            deletedClientDTO.setEntrepriseId(client.getEntreprise().getId());
        }

        return deletedClientDTO;
    }



}
