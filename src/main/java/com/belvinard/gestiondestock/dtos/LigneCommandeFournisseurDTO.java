package com.belvinard.gestiondestock.dtos;

import com.belvinard.gestiondestock.models.CommandeFournisseur;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class LigneCommandeFournisseurDTO {

    private Long id;

    @NotNull(message = "L'article est obligatoire")
    private ArticleDTO articleDetails;

    @NotNull(message = "L'identifiant de la commande fournisseur est obligatoire")
    private Long commandeFournisseurId;

    @JsonIgnore
    private CommandeFournisseurDTO commandeFournisseurDetails;

    @NotNull(message = "La quantité est obligatoire")
    @DecimalMin(value = "0.01", message = "La quantité doit être supérieure à zéro")
    private BigDecimal quantite;

    @NotNull(message = "Le prix unitaire est obligatoire")
    @DecimalMin(value = "0.01", message = "Le prix unitaire doit être supérieur à zéro")
    private BigDecimal prixUnitaire;

    @NotNull(message = "L'entreprise est obligatoire")
    private Long entrepriseId;

    @JsonIgnore
    private EntrepriseDTO entrepriseDetails;
}
