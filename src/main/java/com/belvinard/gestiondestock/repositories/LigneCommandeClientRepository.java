package com.belvinard.gestiondestock.repositories;

import com.belvinard.gestiondestock.models.LigneCommandeClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LigneCommandeClientRepository extends JpaRepository<LigneCommandeClient, Long> {


  List<LigneCommandeClient> findAllByCommandeClientId(Long id);

  List<LigneCommandeClient> findAllByArticleId(Long id);
}
