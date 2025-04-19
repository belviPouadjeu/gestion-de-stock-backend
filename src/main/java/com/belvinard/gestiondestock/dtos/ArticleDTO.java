package com.belvinard.gestiondestock.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    @Schema(hidden = true)
    private Long categoryId;

    @Schema(hidden = true)
    private CategoryDTO categoryDetails;

    @Schema(hidden = true)
    private Long entrepriseId;

    @Schema(hidden = true)
    private EntrepriseDTO entrepriseDetails;

    @Schema(hidden = true)
    private LocalDateTime creationDate;

    @Schema(hidden = true)
    private LocalDateTime lastModifiedDate;

    public Long getEntrepriseId() {
        return entrepriseId;
    }

    public void setEntrepriseId(Long entrepriseId) {
        this.entrepriseId = entrepriseId;
    }

    public EntrepriseDTO getEntrepriseDetails() {
        return entrepriseDetails;
    }

    public void setEntrepriseDetails(EntrepriseDTO entrepriseDetails) {
        this.entrepriseDetails = entrepriseDetails;
    }
}

