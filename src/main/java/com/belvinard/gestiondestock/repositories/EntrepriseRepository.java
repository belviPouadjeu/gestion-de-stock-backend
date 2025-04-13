package com.belvinard.gestiondestock.repositories;

import com.belvinard.gestiondestock.models.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntrepriseRepository extends JpaRepository<Entreprise, Long> {

}
