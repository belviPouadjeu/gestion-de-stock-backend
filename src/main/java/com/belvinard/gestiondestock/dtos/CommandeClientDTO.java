package com.belvinard.gestiondestock.dtos;

import com.belvinard.gestiondestock.models.EtatCommande;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class CommandeClientDTO {

    private Long id;

    @NotBlank(message = "Le code de la commande est obligatoire")
    @Size(min = 4, max = 50, message = "Le code de la commande doit contenir entre 4 et 50 caractères")
    private String code;

    @NotNull(message = "La date de commande est obligatoire")
    private LocalDateTime dateCommande;

    @NotNull(message = "L'état de la commande est obligatoire")
    private EtatCommande etatCommande;

    @Schema(hidden = true)
    private ClientDTO clientDetails;

    @NotNull(message = "L'identifiant de l'entreprise est requis")
    private Long entrepriseId;

    @Schema(hidden = true)
    private List<LigneCommandeClientDTO> ligneCommandeClients;
}

