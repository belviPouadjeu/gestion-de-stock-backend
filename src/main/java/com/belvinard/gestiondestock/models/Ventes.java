package com.belvinard.gestiondestock.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "ventes")
public class Ventes extends AbstractEntity {

  @Column(name = "code")
  private String code;

  @Column(name = "datevente")
  private LocalDateTime dateVente;

  @Column(name = "commentaire")
  private String commentaire;

  @Column(name = "identreprise")
  private Long idEntreprise;

  @OneToMany(mappedBy = "vente")
  private List<LigneVente> ligneVentes;
}
