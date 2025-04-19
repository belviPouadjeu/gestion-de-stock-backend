package com.belvinard.gestiondestock.services.impl;

import com.belvinard.gestiondestock.dtos.LigneCommandeClientDTO;
import com.belvinard.gestiondestock.exceptions.ResourceNotFoundException;
import com.belvinard.gestiondestock.models.Article;
import com.belvinard.gestiondestock.models.CommandeClient;
import com.belvinard.gestiondestock.models.Entreprise;
import com.belvinard.gestiondestock.models.LigneCommandeClient;
import com.belvinard.gestiondestock.repositories.ArticleRepository;
import com.belvinard.gestiondestock.repositories.CommandeClientRepository;
import com.belvinard.gestiondestock.repositories.EntrepriseRepository;
import com.belvinard.gestiondestock.repositories.LigneCommandeClientRepository;
import com.belvinard.gestiondestock.services.LigneCommandeClientService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
//@RequiredArgsConstructor
public class LigneCommandeClientServiceImpl implements LigneCommandeClientService {
    private final CommandeClientRepository commandeClientRepository;
    private final EntrepriseRepository entrepriseRepository;
    private LigneCommandeClientRepository ligneCommandeClientRepository;
    private final ArticleRepository articleRepository;
    private ModelMapper modelMapper;

    public LigneCommandeClientServiceImpl(CommandeClientRepository commandeClientRepository, EntrepriseRepository entrepriseRepository, ArticleRepository articleRepository, LigneCommandeClientRepository ligneCommandeClientRepository, ArticleRepository articleRepository1, ModelMapper modelMapper) {
        this.commandeClientRepository = commandeClientRepository;
        this.entrepriseRepository = entrepriseRepository;
        this.ligneCommandeClientRepository = ligneCommandeClientRepository;
        this.articleRepository = articleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public LigneCommandeClientDTO createLigneCommandeClient(Long commandeId, Long articleId, LigneCommandeClientDTO ligneDTO) {

        // Récupération de la commande client
        CommandeClient commande = commandeClientRepository.findById(commandeId)
                .orElseThrow(() -> new ResourceNotFoundException("Commande client non trouvée avec l'id " + commandeId));

        // Récupération de l'article
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article non trouvé avec l'id " + articleId));

        // Création de l'entité LigneCommandeClient
        LigneCommandeClient ligne = new LigneCommandeClient();
        ligne.setQuantite(ligneDTO.getQuantite());
        ligne.setPrixUnitaire(ligneDTO.getPrixUnitaire());
        ligne.setCommandeClient(commande);
        ligne.setArticle(article);

        // Sauvegarde
        LigneCommandeClient saved = ligneCommandeClientRepository.save(ligne);

        // Retour du DTO
        LigneCommandeClientDTO response = new LigneCommandeClientDTO();
        response.setId(saved.getId());
        response.setQuantite(saved.getQuantite());
        response.setPrixUnitaire(saved.getPrixUnitaire());
        response.setCommandeClientId(commande.getId());
        response.setArticleId(article.getId());

        return response;
    }


    @Override
    public List<LigneCommandeClientDTO> saveAll(Long commandeId, List<LigneCommandeClientDTO> lignesDTO) {
        CommandeClient commande = commandeClientRepository.findById(commandeId)
                .orElseThrow(() -> new ResourceNotFoundException("Commande client non trouvée avec l'id " + commandeId));

        List<LigneCommandeClient> lignes = new ArrayList<>();

        for (LigneCommandeClientDTO ligneDTO : lignesDTO) {
            Article article = articleRepository.findById(ligneDTO.getArticleDetails().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Article non trouvé avec l'id " + ligneDTO.getArticleDetails().getId()));


            LigneCommandeClient ligne = new LigneCommandeClient();
            ligne.setCommandeClient(commande);
            ligne.setPrixUnitaire(ligneDTO.getPrixUnitaire());
            ligne.setQuantite(ligneDTO.getQuantite());

            lignes.add(ligne);
        }

        List<LigneCommandeClient> saved = ligneCommandeClientRepository.saveAll(lignes);

        return saved.stream()
                .map(l -> modelMapper.map(l, LigneCommandeClientDTO.class))
                .collect(Collectors.toList());
    }


    @Override
    public LigneCommandeClientDTO update(Long ligneId, LigneCommandeClientDTO ligneDTO) {
        // 🔍 Vérifie si la ligne existe
        LigneCommandeClient ligneExistante = ligneCommandeClientRepository.findById(ligneId)
                .orElseThrow(() -> new ResourceNotFoundException("Aucune ligne trouvée avec l'ID : " + ligneId));

        // 🔁 Met à jour les champs
        ligneExistante.setQuantite(ligneDTO.getQuantite());
        ligneExistante.setPrixUnitaire(ligneDTO.getPrixUnitaire());

        // 🔁 Mise à jour optionnelle de l'article
        if (ligneDTO.getArticleDetails() != null) {
            ligneExistante.setArticle(modelMapper.map(ligneDTO.getArticleDetails(), Article.class));
        }

        // ✅ Enregistre la mise à jour
        LigneCommandeClient updated = ligneCommandeClientRepository.save(ligneExistante);

        return modelMapper.map(updated, LigneCommandeClientDTO.class);
    }


    @Override
    public LigneCommandeClientDTO delete(Long ligneId) {
        LigneCommandeClient ligne = ligneCommandeClientRepository.findById(ligneId)
                .orElseThrow(() -> new ResourceNotFoundException("Ligne non trouvée avec ID : " + ligneId));

        ligneCommandeClientRepository.delete(ligne);

        return modelMapper.map(ligne, LigneCommandeClientDTO.class);
    }


    @Override
    public LigneCommandeClientDTO findById(Long idLigne) {
        LigneCommandeClient ligne = ligneCommandeClientRepository.findById(idLigne)
                .orElseThrow(() -> new ResourceNotFoundException("Aucune ligne trouvée avec ID : " + idLigne));

        return modelMapper.map(ligne, LigneCommandeClientDTO.class);
    }


    @Override
    public List<LigneCommandeClientDTO> findAll() {
        return ligneCommandeClientRepository.findAll().stream()
                .map(ligne -> modelMapper.map(ligne, LigneCommandeClientDTO.class))
                .collect(Collectors.toList());
    }

}
