package com.belvinard.gestiondestock.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LigneCommandeClientDTO {

    private Long id;

    @NotNull(message = "L'article est obligatoire")
    private ArticleDTO articleDetails;

    @NotNull(message = "L'identifiant de la commande client est obligatoire")
    private Long commandeClientId;

    @JsonIgnore
    private CommandeClientDTO commandeClientDetails;

    @NotNull(message = "La quantité est obligatoire")
    @DecimalMin(value = "0.01", message = "La quantité doit être supérieure à zéro")
    private BigDecimal quantite;

    @NotNull(message = "Le prix unitaire est obligatoire")
    @DecimalMin(value = "0.01", message = "Le prix unitaire doit être supérieur à zéro")
    private BigDecimal prixUnitaire;

    @NotNull(message = "L'identifiant de l'entreprise est obligatoire")
    private Long entrepriseId;
}
