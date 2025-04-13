package com.belvinard.gestiondestock.repositories;

import com.belvinard.gestiondestock.models.LigneVente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LigneVenteRepository extends JpaRepository<LigneVente, Long> {

  List<LigneVente> findAllByArticleId(Long idArticle);

  List<LigneVente> findAllByVenteId(Long id);
}
