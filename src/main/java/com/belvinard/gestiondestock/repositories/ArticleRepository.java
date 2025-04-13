package com.belvinard.gestiondestock.repositories;

import com.belvinard.gestiondestock.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {

  Optional<Article> findArticleByCodeArticle(String codeArticle);

  List<Article> findAllByCategoryId(Long idCategory);

  Optional<Article> findByCodeArticleIgnoreCase(String codeArticle);
}