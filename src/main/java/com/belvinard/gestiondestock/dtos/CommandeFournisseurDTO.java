package com.belvinard.gestiondestock.dtos;

import com.belvinard.gestiondestock.models.EtatCommande;
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
public class CommandeFournisseurDTO {

    private Long id;

    @NotBlank(message = "Le code de la commande est obligatoire")
    @Size(min = 4, max = 20, message = "Le code doit contenir entre 4 et 20 caractères")
    private String code;

    @NotNull(message = "La date de commande est obligatoire")
    private LocalDateTime dateCommande;

    @NotNull(message = "L'état de la commande est obligatoire")
    private EtatCommande etatCommande;

    @NotNull(message = "Le fournisseur est obligatoire")
    private FournisseurDTO fournisseur;

    @NotNull(message = "L'ID de l'entreprise est obligatoire")
    private Long entrepriseId;

    @NotNull(message = "La liste des lignes de commande est obligatoire")
    @Size(min = 1, message = "Il doit y avoir au moins une ligne de commande")
    private List<LigneCommandeFournisseurDTO> ligneCommandeFournisseurs;
}
