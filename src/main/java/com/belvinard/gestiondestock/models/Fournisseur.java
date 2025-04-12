package com.belvinard.gestiondestock.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "fournisseur")
public class Fournisseur extends AbstractEntity {

  @Column(name = "nom")
  private String nom;

  @Column(name = "prenom")
  private String prenom;

  @Embedded
  private Adresse adresse;

  @Column(name = "photo")
  private String photo;

  @Column(name = "mail")
  private String mail;

  @Column(name = "numTel")
  private String numTel;

  @ManyToOne
  @JoinColumn(name = "entrepriseiId")
  private Entreprise entreprise;

  @OneToMany(mappedBy = "fournisseur")
  private List<CommandeFournisseur> commandeFournisseurs;


}
