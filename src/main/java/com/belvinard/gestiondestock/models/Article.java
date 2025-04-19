package com.belvinard.gestiondestock.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "article")
public class Article extends AbstractEntity {

  @NotBlank(message = "Le code article est obligatoire")
  @Size(min = 4, max = 50, message = "Le code article doit etre entre 4 et 50 caractères")
  @Column(name = "codearticle", nullable = false, unique = true)
  private String codeArticle;

  @NotBlank(message = "La désignation est obligatoire")
  @Size(min = 4, max = 100, message = "La désignation doit etre entre 4 et 100 caractères")
  @Column(name = "designation", nullable = false)
  private String designation;

  @ManyToOne(optional = false)
  @JoinColumn(name = "idcategory", nullable = false)
  private Category category;

  @ManyToOne(optional = false)
  @JoinColumn(name = "entrepriseiId", nullable = false)
  private Entreprise entreprise;

  @OneToMany(mappedBy = "article")
  private List<LigneVente> ligneVentes;

  @OneToMany(mappedBy = "article")
  private List<LigneCommandeClient> ligneCommandeClients;

  @OneToMany(mappedBy = "article")
  private List<MvtStk> mvtStks;

  @ManyToOne
  @JoinColumn(name = "commande_id")
  private CommandeFournisseur commandeFournisseur;

  @OneToMany(mappedBy = "article")
  private List<LigneCommandeFournisseur> ligneCommandeFournisseurs;
}
