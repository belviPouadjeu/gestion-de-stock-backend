package com.belvinard.gestiondestock.repositories;

import com.belvinard.gestiondestock.models.CommandeFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommandeFournisseurRepository extends JpaRepository<CommandeFournisseur, Long> {

  Optional<CommandeFournisseur> findCommandeFournisseurByCode(String code);

  List<CommandeFournisseur> findAllByFournisseurId(Long id);
}
