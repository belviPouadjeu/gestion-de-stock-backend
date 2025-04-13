package com.belvinard.gestiondestock.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "categories")
public class Category extends AbstractEntity {

    @NotBlank(message = "La désignation de la catégorie est obligatoire")
    @Size(min = 2, max = 100, message = "La désignation doit contenir entre 2 et 100 caractères")
    @Column(name = "designation")
    private String designation;

    @NotBlank(message = "Le code de la catégorie est obligatoire")
    @Size(min = 2, max = 50, message = "Le code doit contenir entre 2 et 50 caractères")
    @Column(name = "code")
    private String code;

    @ManyToOne
    @JoinColumn(name = "entrepriseiId")
    private Entreprise entreprise;

    @OneToMany(mappedBy = "category")
    private List<Article> articles;
}