package com.belvinard.gestiondestock.dtos;

import com.belvinard.gestiondestock.models.SourceMvtStk;
import com.belvinard.gestiondestock.models.TypeMvtStk;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MvtStkDTO {

    private Integer id;

    @NotNull(message = "La date du mouvement est obligatoire")
    private LocalDateTime dateMvt;

    @NotNull(message = "La quantité est obligatoire")
    @DecimalMin(value = "0.01", message = "La quantité doit être supérieure à zéro")
    private BigDecimal quantite;

    @NotNull(message = "L'article est obligatoire")
    private ArticleDTO article;

    @NotNull(message = "Le type de mouvement est obligatoire")
    private TypeMvtStk typeMvt;

    @NotNull(message = "La source du mouvement est obligatoire")
    private SourceMvtStk sourceMvt;

    @NotNull(message = "L'entreprise est obligatoire")
    private Integer entrepriseId;
}
