package com.belvinard.gestiondestock.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    @Schema(hidden = true)
    private Long id;

    @NotBlank(message = "La désignation de la catégorie est obligatoire")
    @Size(min = 4, max = 100, message = "La désignation doit contenir entre 4 et 100 caractères")
    private String designation;

    @NotBlank(message = "Le code de la catégorie est obligatoire")
    @Size(min = 2, max = 50, message = "Le code doit contenir entre 2 et 50 caractères")
    private String code;

    @Schema(hidden = true)
    private Long entrepriseId;

    @Schema(hidden = true)
    private Long articleId;

    @Schema(hidden = true)
    private LocalDateTime creationDate;

    @Schema(hidden = true)
    private LocalDateTime lastModifiedDate;
}