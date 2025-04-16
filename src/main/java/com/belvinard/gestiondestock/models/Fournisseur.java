package com.belvinard.gestiondestock.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "fournisseur")
public class Fournisseur extends AbstractEntity {

  @NotBlank(message = "Le nom du client est obligatoire")
  @Size(min = 4, max = 100, message = "Le nom doit contenir entre 4 et 100 caractères")
  @Column(name = "nom")
  private String nom;

  @NotBlank(message = "Le prénom du client est obligatoire")
  @Size(min = 4, max = 100, message = "Le prénom doit contenir 4 et 100 caractères")
  @Column(name = "prenom")
  private String prenom;

  @Embedded
  private Adresse adresse;

  @Pattern(
          regexp = ".*\\.(jpg|jpeg|png|gif|bmp)$",
          message = "Le nom du fichier photo doit être une image (jpg, jpeg, png, gif, bmp)"
  )
  @Column(name = "photo")
  private String photo;

  @NotBlank(message = "L'Email du client est obligatoire")
  @Email(message = "L'adresse email est invalide")
  @Column(name = "mail")
  private String mail;

  @Pattern(
          regexp = "^[0-9+\\s().-]{6,20}$",
          message = "Le numéro de téléphone doit contenir entre 6 et 20 caractères " +
                  "et peut inclure des chiffres, espaces, +, (), ou -"
  )
  @Column(name = "numTel")
  private String numTel;

  @ManyToOne
  @JoinColumn(name = "entrepriseiId")
  private Entreprise entreprise;

  @OneToMany(mappedBy = "fournisseur")
  private List<CommandeFournisseur> commandeFournisseurs;


}
