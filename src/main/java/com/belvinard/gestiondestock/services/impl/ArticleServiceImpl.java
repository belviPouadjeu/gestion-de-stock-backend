package com.belvinard.gestiondestock.services.impl;

import com.belvinard.gestiondestock.dtos.*;
import com.belvinard.gestiondestock.exceptions.APIException;
import com.belvinard.gestiondestock.exceptions.ResourceNotFoundException;
import com.belvinard.gestiondestock.models.*;
import com.belvinard.gestiondestock.repositories.*;
import com.belvinard.gestiondestock.services.ArticleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final EntepriseRepository entepriseRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final LigneCommandeClientRepository ligneCommandeClientRepository;
    private final LigneVenteRepository ligneVenteRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository, EntepriseRepository entepriseRepository,
                              CategoryRepository categoryRepository, ModelMapper modelMapper, LigneVenteRepository venteRepository,
                              LigneCommandeClientRepository commandeClientRepository, LigneCommandeClientRepository ligneCommandeClientRepository, LigneVenteRepository ligneVenteRepository) {
        this.articleRepository = articleRepository;
        this.entepriseRepository = entepriseRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.ligneCommandeClientRepository = ligneCommandeClientRepository;
        this.ligneVenteRepository = ligneVenteRepository;
    }

   /* ================== CREATE ARTICLE ================== */
    @Override
    public ArticleDTO createArticle(Long entrepriseId, Long categoryId, ArticleDTO articleDTO) {

        // Vérifie que l'entreprise existe
        Entreprise entreprise = entepriseRepository.findById(entrepriseId)
                .orElseThrow(() -> new ResourceNotFoundException("Entreprise", "id", entrepriseId));

        // Vérifie que la catégorie existe
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        // Mapping du DTO vers l'entité
        Article article = modelMapper.map(articleDTO, Article.class);
        article.setEntreprise(entreprise);
        article.setCategory(category);

        // Calcul automatique du prix TTC
//        if (article.getPrixUnitaireHt() != null && article.getTauxTva() != null) {
//            BigDecimal tva = article.getPrixUnitaireHt()
//                    .multiply(article.getTauxTva())
//                    .divide(BigDecimal.valueOf(100));
//            article.setPrixUnitaireTtc(article.getPrixUnitaireHt().add(tva));
//        }

        // Sauvegarde
        Article saved = articleRepository.save(article);

        // Conversion en DTO
        ArticleDTO responseDTO = modelMapper.map(saved, ArticleDTO.class);
        responseDTO.setCategoryId(saved.getCategory().getId());
        responseDTO.setEntrepriseId(saved.getEntreprise().getId());
        responseDTO.setCategoryDetails(modelMapper.map(saved.getCategory(), CategoryDTO.class));

        return responseDTO;
    }

    /* ================== GET ALL ARTICLES ================== */
    @Override
    public List<ArticleDTO> getAllArticles() {
        List<Article> articles = articleRepository.findAll();

        return articles.stream().map(article -> {
            ArticleDTO dto = modelMapper.map(article, ArticleDTO.class);

            if (article.getEntreprise() != null) {
                dto.setEntrepriseId(article.getEntreprise().getId());
            }

            if (article.getCategory() != null) {
                dto.setCategoryId(article.getCategory().getId());
                dto.setCategoryDetails(modelMapper.map(article.getCategory(), CategoryDTO.class));
            }

            return dto;
        }).collect(Collectors.toList());
    }

    /* ================== FIND ARTICLE BY ID ================== */
    @Override
    public ArticleDTO findAllByArticleId(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", id));

        ArticleDTO dto = modelMapper.map(article, ArticleDTO.class);

        if (article.getCategory() != null) {
            dto.setCategoryId(article.getCategory().getId());
            dto.setCategoryDetails(modelMapper.map(article.getCategory(), CategoryDTO.class));
        }

        dto.setEntrepriseId(article.getEntreprise().getId());

        return dto;
    }

    /* ================== DELETE ARTICLE ================== */

//    @Override
//    public ArticleDTO deleteArticle(Long id) {
//        Article article = articleRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", id));
//
//        ArticleDTO dto = modelMapper.map(article, ArticleDTO.class);
//
//        if (article.getEntreprise() != null) {
//            dto.setEntrepriseId(article.getEntreprise().getId());
//        }
//
//        if (article.getCategory() != null) {
//            dto.setCategoryId(article.getCategory().getId());
//            dto.setCategoryDetails(modelMapper.map(article.getCategory(), CategoryDTO.class));
//        }
//
//        articleRepository.delete(article);
//
//        List<LigneCommandeClient> ligneCommandeClients = ligneCommandeClientRepository.findAllByArticleId(id);
//        if (!ligneCommandeClients.isEmpty()) {
//            throw new APIException("Impossible de supprimer un article deja utilise dans des commandes client");
//        }
//        List<LigneCommandeFournisseur> ligneCommandeFournisseurs = ligneCommandeFournisseurRepository.findAllByArticleId(id);
//        if (!ligneCommandeFournisseurs.isEmpty()) {
//            throw new APIException("Impossible de supprimer un article deja utilise dans des commandes fournisseur");
//        }
//        List<LigneVente> ligneVentes = ligneVenteRepository.findAllByArticleId(id);
//        if (!ligneVentes.isEmpty()) {
//            throw new APIException("Impossible de supprimer un article deja utilise dans des ventes");
//        }
//
//        return dto;
//    }

    @Override
    public ArticleDTO deleteArticle(Long id) {
        // Recherche de l'article
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", id));

        // Vérifie s'il est utilisé ailleurs avant suppression
        checkIfArticleUsedElseThrow(id);

        // Préparation du DTO de réponse
        ArticleDTO dto = modelMapper.map(article, ArticleDTO.class);

        if (article.getEntreprise() != null) {
            dto.setEntrepriseId(article.getEntreprise().getId());
        }

        if (article.getCategory() != null) {
            dto.setCategoryId(article.getCategory().getId());
            dto.setCategoryDetails(modelMapper.map(article.getCategory(), CategoryDTO.class));
        }

        // Suppression réelle de l'article
        articleRepository.delete(article);

        return dto;
    }


    /**
     * Vérifie si l'article est utilisé dans des lignes de commande ou de vente,
     * et lève une exception si c'est le cas.
     */
    private void checkIfArticleUsedElseThrow(Long idArticle) {
        List<LigneCommandeClient> ligneCommandeClients = ligneCommandeClientRepository.findAllByArticleId(idArticle);
        if (!ligneCommandeClients.isEmpty()) {
            throw new APIException("Impossible de supprimer un article déjà utilisé dans des commandes client");
        }

//        List<LigneCommandeFournisseur> ligneCommandeFournisseurs = ligneCommandeFournisseurRepository.findAllByArticleId(idArticle);
//        if (!ligneCommandeFournisseurs.isEmpty()) {
//            throw new APIException("Impossible de supprimer un article déjà utilisé dans des commandes fournisseur");
//        }

        List<LigneVente> ligneVentes = ligneVenteRepository.findAllByArticleId(idArticle);
        if (!ligneVentes.isEmpty()) {
            throw new APIException("Impossible de supprimer un article déjà utilisé dans des ventes");
        }
    }


    /* ================== FIND ARTICLE BY CODE ================== */

    @Override
    public ArticleDTO findByCodeArticle(String codeArticle) {
        // Vérifie si l'article existe avec le code fourni
        Article article = articleRepository.findByCodeArticleIgnoreCase(codeArticle)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "codeArticle", codeArticle));

        // Mapping vers le DTO
        ArticleDTO dto = modelMapper.map(article, ArticleDTO.class);

        // Ajout des infos enrichies
        if (article.getCategory() != null) {
            dto.setCategoryId(article.getCategory().getId());
            dto.setCategoryDetails(modelMapper.map(article.getCategory(), CategoryDTO.class));
        }

        dto.setEntrepriseId(article.getEntreprise().getId());

        return dto;
    }


    /* ================== FIND ARTICLE BY CATEGORY ================== */

    @Override
    public List<ArticleDTO> findAllArticleByIdCategory(Long idCategory) {
        // Vérifie si la catégorie existe
        Category category = categoryRepository.findById(idCategory)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", idCategory));

        // Récupère tous les articles liés à cette catégorie
        List<Article> articles = articleRepository.findAllByCategoryId(idCategory);

        // Transforme chaque entité en DTO enrichi
        return articles.stream().map(article -> {
            ArticleDTO dto = modelMapper.map(article, ArticleDTO.class);
            dto.setCategoryId(idCategory);
            dto.setCategoryDetails(modelMapper.map(category, CategoryDTO.class));
            dto.setEntrepriseId(article.getEntreprise().getId());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<LigneVenteDTO> findHistoriqueVentes(Long idArticle) {

        // Vérifie si l'article existe
        Article article = articleRepository.findById(idArticle)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", idArticle));

        // Récupère toutes les lignes de vente associées à l'article
        List<LigneVente> lignesVente = ligneVenteRepository.findAllByArticleId(idArticle);

        //Mappe les entités LigneVente vers des DTOs LigneVenteDTO
        return lignesVente.stream()
                .map(ligneVente -> modelMapper.map(ligneVente, LigneVenteDTO.class))
                .collect(Collectors.toList());
    }


    @Override
    public List<LigneCommandeClientDTO> findHistoriaueCommandeClient(Long idArticle) {
        Article article = articleRepository.findById(idArticle)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", idArticle));

        List<LigneCommandeClient> lignes = ligneCommandeClientRepository.findAllByArticleId(idArticle);

        return lignes.stream()
                .map(ligne -> modelMapper.map(ligne, LigneCommandeClientDTO.class))
                .collect(Collectors.toList());
    }

//    @Override
//    public List<LigneCommandeFournisseurDTO> findHistoriqueCommandeFournisseur(Long idArticle) {
//        Article article = articleRepository.findById(idArticle)
//                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", idArticle));
//
//        List<LigneCommandeFournisseur> lignes = ligneCommandeFournisseurRepository.findAllByArticleId(idArticle);
//
//        return lignes.stream()
//                .map(ligne -> modelMapper.map(ligne, LigneCommandeFournisseurDTO.class))
//                .collect(Collectors.toList());
//    }


}
