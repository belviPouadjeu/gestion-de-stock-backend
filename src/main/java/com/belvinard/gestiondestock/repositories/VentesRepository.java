package com.belvinard.gestiondestock.repositories;

import com.belvinard.gestiondestock.models.Ventes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VentesRepository extends JpaRepository<Ventes,Long> {
    Optional<Ventes> findByCode(String code);

}
