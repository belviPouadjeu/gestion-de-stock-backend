package com.belvinard.gestiondestock.services.impl;

import com.belvinard.gestiondestock.dtos.LigneVenteDTO;
import com.belvinard.gestiondestock.exceptions.APIException;
import com.belvinard.gestiondestock.exceptions.ResourceNotFoundException;
import com.belvinard.gestiondestock.models.Article;
import com.belvinard.gestiondestock.models.EtatVente;
import com.belvinard.gestiondestock.models.LigneVente;
import com.belvinard.gestiondestock.models.Vente;
import com.belvinard.gestiondestock.repositories.ArticleRepository;
import com.belvinard.gestiondestock.repositories.LigneVenteRepository;
import com.belvinard.gestiondestock.repositories.VentesRepository;
import com.belvinard.gestiondestock.responses.LigneVentResponse;
import com.belvinard.gestiondestock.services.LigneVenteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LigneVenteServiceImpl implements LigneVenteService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private LigneVenteRepository ligneVenteRepository;

    @Autowired
    private VentesRepository ventesRepository;

    public LigneVenteServiceImpl(ModelMapper modelMapper, ArticleRepository articleRepository,
                                 LigneVenteRepository ligneVenteRepository, VentesRepository ventesRepository) {
        this.modelMapper = modelMapper;
        this.articleRepository = articleRepository;
        this.ligneVenteRepository = ligneVenteRepository;
        this.ventesRepository = ventesRepository;
    }

    @Override
    public LigneVentResponse getAllLigneVente() {
        List<LigneVente> ligneVentes = ligneVenteRepository.findAll();

        if (ligneVentes.isEmpty()){
            throw new APIException("No ligneVents created until now !");
        }

        List<LigneVenteDTO> ligneVenteDTOS = ligneVentes.stream()
                .map(ligneVente -> modelMapper.map(ligneVente, LigneVenteDTO.class))
                .toList();
        LigneVentResponse ligneVentResponse = new LigneVentResponse();
        ligneVentResponse.setContent(ligneVenteDTOS);

        return ligneVentResponse;
    }

    @Override
    public LigneVenteDTO createLigneVente(LigneVenteDTO ligneVenteDTO, Long venteId, Long articleId) {
        // DTO to Entity
        LigneVente ligneVente = modelMapper.map(ligneVenteDTO, LigneVente.class);

        // Récupération de l'article par son ID
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article non trouve avec l'Id " + articleId));

        // Récupération de l'article par son ID
        Vente vente = ventesRepository.findById(venteId)
                        .orElseThrow(() -> new ResourceNotFoundException("Vente non trouvee avec l'Id " + venteId));

        ligneVente.setArticle(article);
        ligneVente.setVente(vente);

        // Save the new Ligne vente to the database
        LigneVente savedLignevente = ligneVenteRepository.save(ligneVente);

        // Convert back to DTO
        return modelMapper.map(savedLignevente, LigneVenteDTO.class);

    }

    @Override
    public LigneVentResponse findAllByVenteId(Long venteId) {
        // 1. Verify the Vente exists
        Optional<Vente> ventes = ventesRepository.findById(venteId);
        if (ventes.isEmpty()) {
            throw new APIException("Aucune vente trouvée avec l'identifiant : " + venteId);
        }

        // 2. Fetch LigneVente by venteId
        List<LigneVente> ligneVentes = ligneVenteRepository.findByVenteId(venteId);

        // 3. Map entities to DTOs
        List<LigneVenteDTO> ligneVenteDTOS = ligneVentes.stream()
                .map(ligneVente -> modelMapper.map(ligneVente, LigneVenteDTO.class))
                .toList();
        LigneVentResponse ligneVentResponse = new LigneVentResponse();
        ligneVentResponse.setContent(ligneVenteDTOS);

        return ligneVentResponse;
    }

    public LigneVenteDTO updateLigneVente(Long ligneVenteId, Long venteId, Long articleId, LigneVenteDTO dto) {
        LigneVente existingLigne = ligneVenteRepository.findById(ligneVenteId)
                .orElseThrow(() -> new APIException("Ligne de vente non trouvée avec l'id: " + ligneVenteId));

        Vente currentVente = existingLigne.getVente();
        if (currentVente.getEtatVente() == EtatVente.FINALISEE) {
            throw new APIException("Impossible de modifier cette ligne car la vente est finalisée.");
        }

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new APIException("Article non trouvé avec l'id: " + articleId));

        Vente newVente = ventesRepository.findById(venteId)
                .orElseThrow(() -> new APIException("Vente non trouvée avec l'id: " + venteId));

        existingLigne.setQuantite(dto.getQuantite());
        existingLigne.setPrixUnitaire(dto.getPrixUnitaire());
        existingLigne.setArticle(article);
        existingLigne.setVente(newVente);

        LigneVente saved = ligneVenteRepository.save(existingLigne);
        return modelMapper.map(saved, LigneVenteDTO.class);
    }

    @Override
    public LigneVenteDTO deleteLigneVente(Long ligneVenteId) {
        LigneVente existingLigne = ligneVenteRepository.findById(ligneVenteId)
                .orElseThrow(() -> new APIException("Ligne de vente non trouvée avec l'id: " + ligneVenteId));

        ligneVenteRepository.delete(existingLigne);

        // Convert deleted Entity to DTO

        return modelMapper.map(existingLigne, LigneVenteDTO.class);

    }


}
