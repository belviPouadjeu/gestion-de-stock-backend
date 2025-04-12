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




}
