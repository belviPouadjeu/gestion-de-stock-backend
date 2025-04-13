package com.belvinard.gestiondestock.services.impl;

import com.belvinard.gestiondestock.dtos.ArticleDTO;
import com.belvinard.gestiondestock.dtos.CategoryDTO;
import com.belvinard.gestiondestock.exceptions.ResourceNotFoundException;
import com.belvinard.gestiondestock.models.Article;
import com.belvinard.gestiondestock.models.Category;
import com.belvinard.gestiondestock.models.Entreprise;
import com.belvinard.gestiondestock.repositories.ArticleRepository;
import com.belvinard.gestiondestock.repositories.CategoryRepository;
import com.belvinard.gestiondestock.repositories.EntepriseRepository;
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

    public ArticleServiceImpl(ArticleRepository articleRepository, EntepriseRepository entepriseRepository,
                              CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.articleRepository = articleRepository;
        this.entepriseRepository = entepriseRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
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
        if (article.getPrixUnitaireHt() != null && article.getTauxTva() != null) {
            BigDecimal tva = article.getPrixUnitaireHt()
                    .multiply(article.getTauxTva())
                    .divide(BigDecimal.valueOf(100));
            article.setPrixUnitaireTtc(article.getPrixUnitaireHt().add(tva));
        }

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

    /* ================== DELETE ARTICLE ================== */

    @Override
    public ArticleDTO deleteArticle(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", id));

        ArticleDTO dto = modelMapper.map(article, ArticleDTO.class);

        if (article.getEntreprise() != null) {
            dto.setEntrepriseId(article.getEntreprise().getId());
        }

        if (article.getCategory() != null) {
            dto.setCategoryId(article.getCategory().getId());
            dto.setCategoryDetails(modelMapper.map(article.getCategory(), CategoryDTO.class));
        }

        articleRepository.delete(article);

        return dto;
    }


    /* ================== FIND ARTICLE BY ID ================== */
    @Override
    public ArticleDTO findById(Long id) {
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



}
