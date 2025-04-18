package com.belvinard.gestiondestock.dtos;

import com.belvinard.gestiondestock.models.EtatCommande;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommandeFournisseurDTO {

    @Schema(hidden = true)
    private Long id;

    @NotBlank(message = "Le code de la commande est obligatoire")
    @Size(min = 1, max = 50, message = "Le code de la commande doit avoir entre 1 et 50 caractères")
    private String code;

    private LocalDateTime dateCommande;

    @NotNull(message = "L'état de la commande est obligatoire")
    private EtatCommande etatCommande;

    // Ce champ est automatiquement rempli côté backend
    @Schema(hidden = true)
    private FournisseurDTO fournisseurDetails;

    @Override
    public String toString() {
        return "CommandeFournisseurDTO{" +
                "code='" + code + '\'' +
                ", etatCommande=" + etatCommande +
                '}';
    }


//    @Schema(hidden = true)
//    private Long fournisseurId;


    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(LocalDateTime dateCommande) {
        this.dateCommande = dateCommande;
    }

    public EtatCommande getEtatCommande() {
        return etatCommande;
    }

    public void setEtatCommande(EtatCommande etatCommande) {
        this.etatCommande = etatCommande;
    }

    public FournisseurDTO getFournisseurDetails() {
        return fournisseurDetails;
    }

    public void setFournisseurDetails(FournisseurDTO fournisseurDetails) {
        this.fournisseurDetails = fournisseurDetails;
    }




}
