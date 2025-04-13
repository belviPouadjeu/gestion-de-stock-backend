package com.belvinard.gestiondestock.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "lignecommandefournisseur")
public class LigneCommandeFournisseur extends AbstractEntity {

  @NotNull(message = "L'article est obligatoire")
  @ManyToOne
  @JoinColumn(name = "idarticle")
  private Article article;

  @NotNull(message = "La commande fournisseur est obligatoire")
  @ManyToOne
  @JoinColumn(name = "idcommandefournisseur")
  private CommandeFournisseur commandeFournisseur;

  @NotNull(message = "La quantité est obligatoire")
  @DecimalMin(value = "0.01", message = "La quantité doit être supérieure à zéro")
  @Column(name = "quantite")
  private BigDecimal quantite;

  @NotNull(message = "Le prix unitaire est obligatoire")
  @DecimalMin(value = "0.01", message = "Le prix unitaire doit être supérieur à zéro")
  @Column(name = "prixunitaire")
  private BigDecimal prixUnitaire;

  @NotNull(message = "L'entreprise est obligatoire")
  @ManyToOne
  @JoinColumn(name = "identreprise")
  private Entreprise entreprise;
}
