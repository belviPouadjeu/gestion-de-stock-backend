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
@Schema(description = "DTO représentant une ligne de commande client")
public class LigneCommandeClientDTO {

    @Schema(description = "Identifiant unique de la ligne de commande")
    private Long id;

    @NotNull(message = "L'article est obligatoire")
    @Schema(description = "Détails de l'article associé à la ligne", required = true)
    private ArticleDTO articleDetails;

    @NotNull(message = "L'identifiant de la commande client est obligatoire")
    @Schema(description = "Identifiant de la commande client à laquelle appartient cette ligne", required = true, example = "1")
    private Long commandeClientId;

    @JsonIgnore
    @Schema(description = "Détails de la commande client (utilisé en interne, non exposé)")
    private CommandeClientDTO commandeClientDetails;

    @NotNull(message = "La quantité est obligatoire")
    @DecimalMin(value = "0.01", message = "La quantité doit être supérieure à zéro")
    @Schema(description = "Quantité commandée", required = true, example = "5.0")
    private BigDecimal quantite;

    @NotNull(message = "Le prix unitaire est obligatoire")
    @DecimalMin(value = "0.01", message = "Le prix unitaire doit être supérieur à zéro")
    @Schema(description = "Prix unitaire de l'article", required = true, example = "10.50")
    private BigDecimal prixUnitaire;

    @NotNull(message = "l'entreprise est obligatoire")
    @Schema(description = "Détails de l'entreprise associée à la ligne", required = true)
    private EntrepriseDTO entrepriseDetails;


    @NotNull(message = "L'identifiant de l'entreprise est obligatoire")
    @Schema(description = "Identifiant de l'entreprise à laquelle appartient la commande", required = true, example = "1")
    private Long entrepriseId;

}
