//package com.belvinard.gestiondestock.models;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.Table;
//import jakarta.validation.constraints.NotNull;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.NoArgsConstructor;
//
//import java.math.BigDecimal;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@EqualsAndHashCode(callSuper = true)
//@Table(name = "ligneCommandeFournisseurs")
//public class LigneCommandeFournisseur extends AbstractEntity {
//
//    private BigDecimal prixUnitaireHt;
//
//    private BigDecimal tauxTva;
//
//    private BigDecimal prixUnitaireTtc;
//
//    private BigDecimal quantite;
//
//    private String photo; // si pertinent
//
//    @ManyToOne
//    @JoinColumn(name = "commande_id", nullable = false)
//    private CommandeFournisseur commandeFournisseur;
//
//    @ManyToOne
//    @JoinColumn(name = "article_id", nullable = false)
//    private Article article;
//
//
//}