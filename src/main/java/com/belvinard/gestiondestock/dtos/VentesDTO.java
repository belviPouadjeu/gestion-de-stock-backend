package com.belvinard.gestiondestock.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VentesDTO {

    private Long id;

    @NotBlank(message = "Le code de la vente est obligatoire")
    @Size(min = 2, max = 50, message = "Le code doit contenir entre 2 et 50 caractères")
    private String code;

    @NotNull(message = "La date de vente est obligatoire")
    private LocalDateTime dateVente;

    @Size(max = 500, message = "Le commentaire ne doit pas dépasser 500 caractères")
    private String commentaire;

    private List<LigneVenteDTO> ligneVentes;

    @NotNull(message = "L'identifiant de l'entreprise est obligatoire")
    private Long entrepriseId;
}

