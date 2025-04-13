package com.belvinard.gestiondestock.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LigneVenteDTO {

    private Long id;

    @NotNull(message = "La vente est obligatoire")
    private VentesDTO vente;

    @NotNull(message = "L'article est obligatoire")
    private ArticleDTO article;

    @NotNull(message = "La quantité est obligatoire")
    @DecimalMin(value = "0.01", message = "La quantité doit être supérieure à zéro")
    private BigDecimal quantite;

    @NotNull(message = "Le prix unitaire est obligatoire")
    @DecimalMin(value = "0.01", message = "Le prix unitaire doit être supérieur à zéro")
    private BigDecimal prixUnitaire;

    @NotNull(message = "L'entreprise est obligatoire")
    private Long entrepriseId;
}
