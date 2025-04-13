package com.belvinard.gestiondestock.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

  @NotBlank(message = "Le code de la vente est obligatoire")
  @Size(min = 4, max = 50, message = "Le code doit contenir entre 4 et 50 caractères")
  @Column(name = "code")
  private String code;

  @NotNull(message = "La date de vente est obligatoire")
  @Column(name = "datevente")
  private LocalDateTime dateVente;

  @Size(max = 500, message = "Le commentaire ne doit pas dépasser 500 caractères")
  @Column(name = "commentaire")
  private String commentaire;

  @NotNull(message = "L'identifiant de l'entreprise est obligatoire")
  @Column(name = "identreprise")
  private Long idEntreprise;

  @OneToMany(mappedBy = "vente")
  private List<LigneVente> ligneVentes;
}