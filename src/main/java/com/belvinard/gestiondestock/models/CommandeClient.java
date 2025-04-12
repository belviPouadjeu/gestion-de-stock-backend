package com.belvinard.gestiondestock.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "commandeclient")
public class CommandeClient extends AbstractEntity {

  @Column(name = "code")
  private String code;

  @Column(name = "datecommande")
  private LocalDateTime dateCommande;

  @Column(name = "etatcommande")
  @Enumerated(EnumType.STRING)
  private EtatCommande etatCommande;

  @ManyToOne
  @JoinColumn(name = "entrepriseiId")
  private Entreprise entreprise;

  @ManyToOne
  @JoinColumn(name = "idclient")
  private Client client;

  @OneToMany(mappedBy = "commandeClient")
  private List<LigneCommandeClient> ligneCommandeClients;

}
