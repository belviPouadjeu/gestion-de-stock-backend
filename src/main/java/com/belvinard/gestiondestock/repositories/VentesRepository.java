package com.belvinard.gestiondestock.repositories;

import com.belvinard.gestiondestock.models.Vente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VentesRepository extends JpaRepository<Vente,Long> {

    Vente findByCode(String code);
}
