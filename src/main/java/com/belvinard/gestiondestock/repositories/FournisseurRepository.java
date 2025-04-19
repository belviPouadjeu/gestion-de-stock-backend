package com.belvinard.gestiondestock.repositories;

import com.belvinard.gestiondestock.models.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FournisseurRepository extends JpaRepository<Fournisseur, Long> {

}
