package com.belvinard.gestiondestock.services;

import com.belvinard.gestiondestock.dtos.ArticleDTO;
import com.belvinard.gestiondestock.dtos.LigneCommandeClientDTO;
//import com.belvinard.gestiondestock.dtos.LigneVenteDTO;

import java.util.List;

public interface ArticleService {

  ArticleDTO createArticle(Long entrepriseId, Long categoryId, ArticleDTO articleDTO);

 List<ArticleDTO> getAllArticles();

 ArticleDTO deleteArticle(Long id);
 ArticleDTO findAllByArticleId(Long id);

 ArticleDTO findByCodeArticle(String codeArticle);
 List<ArticleDTO> findAllArticleByIdCategory(Long idCategory);

 //List<LigneVenteDTO> findHistoriqueVentes(Long idArticle);

 List<LigneCommandeClientDTO> findHistoriaueCommandeClient(Long idArticle);





}
