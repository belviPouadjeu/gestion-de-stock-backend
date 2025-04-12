package com.belvinard.gestiondestock.dtos;

import com.belvinard.gestiondestock.models.Adresse;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntrepriseDTO {
    private Long id;

    @NotBlank(message = "Le nom de l'entreprise est obligatoire")
    @Size(min = 5, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
    private String nom;

    @NotBlank(message = "La description est obligatoire")
    @Size(min = 5, max = 200, message = "La description doit contenir entre 5 et 200 caractères")
    private String description;

    private Adresse adresse;

    @NotBlank(message = "Le code fiscal est obligatoire")
    @Size(min = 5, max = 20, message = "Le code fiscal doit contenir entre 5 et 20 caractères")
    private String codeFiscal;

    private String photo;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    private String email;

    @NotBlank(message = "Le numéro de téléphone est obligatoire")
    @Size(min = 10, max = 20, message = "Le numéro de téléphone doit contenir entre 10 et 20 caractères")
    private String numTel;

    @Size(max = 150, message = "Le site web doit contenir au maximum 150 caractères")
    private String steWeb;


}