package com.belvinard.gestiondestock.dtos;

import com.belvinard.gestiondestock.models.Category;
import com.belvinard.gestiondestock.models.Entreprise;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDTO {

    @Schema(hidden = true)
    private Long id;

    @NotBlank(message = "Le code de l'article est obligatoire")
    @Size(min = 4, max = 50, message = "Le code article doit etre entre 4 et 50 caractères")
    private String codeArticle;

    @NotBlank(message = "La désignation est obligatoire")
    @Size(min = 4, max = 100, message = "La désignation doit etre entre 4 et 100 caractères")
    private String designation;

    @NotNull(message = "Le prix unitaire HT est obligatoire")
    @DecimalMin(value = "0.0", inclusive = false, message = "Le prix unitaire HT doit être positif")
    private BigDecimal prixUnitaireHt;

    @NotNull(message = "Le taux de TVA est obligatoire")
    @DecimalMin(value = "0.0", message = "Le taux de TVA ne peut pas être négatif")
    private BigDecimal tauxTva;

    // Le prix TTC peut être calculé automatiquement, donc pas forcément à valider ici
    private BigDecimal prixUnitaireTtc;

    private String photo;

    @Schema(hidden = true)
    private Long categoryId;

    @Schema(hidden = true)
    private CategoryDTO categoryDetails;

    @Schema(hidden = true)
    private Long entrepriseId;
}

