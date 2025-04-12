package com.belvinard.gestiondestock.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "article")
public class Article extends AbstractEntity {

  @Column(name = "codearticle")
  private String codeArticle;

  @Column(name = "designation")
  private String designation;

  @Column(name = "prixunitaireht")
  private BigDecimal prixUnitaireHt;

  @Column(name = "tauxtva")
  private BigDecimal tauxTva;

  @Column(name = "prixunitairettc")
  private BigDecimal prixUnitaireTtc;

  @Column(name = "photo")
  private String photo;

  @ManyToOne
  @JoinColumn(name = "idcategory")
  private Category category;

  @ManyToOne
  @JoinColumn(name = "entrepriseiId")
  private Entreprise entreprise;



}