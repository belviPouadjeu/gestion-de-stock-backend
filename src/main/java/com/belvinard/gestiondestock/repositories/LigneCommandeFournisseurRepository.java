package com.belvinard.gestiondestock.repositories;

import com.belvinard.gestiondestock.models.LigneCommandeFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LigneCommandeFournisseurRepository extends JpaRepository<LigneCommandeFournisseur, Long> {

    List<LigneCommandeFournisseur> findAllByCommandeFournisseurId(Long commandeFournisseurId);

    List<LigneCommandeFournisseur> findAllByArticleId(Long articleId);
}

