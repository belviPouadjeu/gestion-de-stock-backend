package com.belvinard.gestiondestock.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "mvtstk")
public class MvtStk extends AbstractEntity {

  @Column(name = "datemvt")
  private LocalDateTime dateMvt;

  @Column(name = "quantite")
  private BigDecimal quantite;

  @ManyToOne
  @JoinColumn(name = "idarticle")
  private Article article;

  @Column(name = "typemvt")
  @Enumerated(EnumType.STRING)
  private TypeMvtStk typeMvt;

  @Column(name = "sourcemvt")
  @Enumerated(EnumType.STRING)
  private SourceMvtStk sourceMvt;

  @Column(name = "identreprise")
  private Long idEntreprise;
}
