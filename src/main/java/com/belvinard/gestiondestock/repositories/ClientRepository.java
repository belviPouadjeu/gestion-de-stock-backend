package com.belvinard.gestiondestock.repositories;

import com.belvinard.gestiondestock.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
