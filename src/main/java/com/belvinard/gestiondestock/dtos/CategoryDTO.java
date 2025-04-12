package com.belvinard.gestiondestock.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
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

    private String designation;

    private String code;

    @Schema(hidden = true)
    private Long entrepriseId;

    @Schema(hidden = true)
    private LocalDateTime creationDate;

    @Schema(hidden = true)
    private LocalDateTime lastModifiedDate;
}
