package com.belvinard.gestiondestock.models;

import jakarta.persistence.*;
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
public class Category extends AbstractEntity{
    @Column(name = "designation")
    private String designation;

    @Column(name = "code")
    private String code;

    @ManyToOne
    @JoinColumn(name = "entrepriseiId")
    private Entreprise entreprise;

//    @OneToMany(mappedBy = "category")
//    private List<Article> articles;

}