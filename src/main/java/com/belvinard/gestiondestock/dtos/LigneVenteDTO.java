//package com.belvinard.gestiondestock.dtos;
//
//import io.swagger.v3.oas.annotations.media.Schema;
//import jakarta.validation.constraints.DecimalMin;
//import jakarta.validation.constraints.NotNull;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class LigneVenteDTO {
//
//    private Long id;
//
//    @Schema(hidden = true)
//    private LocalDateTime creationDate;
//
//    @Schema(hidden = true)
//    private LocalDateTime lastModifiedDate;
//
//    @Schema(hidden = true)
//    private Long articleId;
//
//    @NotNull(message = "L'article est obligatoire")
//    private ArticleDTO articleDetails;
//
//    @NotNull(message = "La quantité est obligatoire")
//    @DecimalMin(value = "0.01", message = "La quantité doit être supérieure à zéro")
//    private BigDecimal quantite;
//
//    @NotNull(message = "Le prix unitaire est obligatoire")
//    @DecimalMin(value = "0.01", message = "Le prix unitaire doit être supérieur à zéro")
//    private BigDecimal prixUnitaire;
//
//    @NotNull(message = "L'entreprise est obligatoire")
//    private Long entrepriseId;
//
//    @Schema(hidden = true)
//    private EntrepriseDTO entrepriseDetails;
//
//    @Schema(hidden = true)
//    private Long venteId;
//
//    @Schema(hidden = true)
//    private VentesDTO venteDetails;
//}
