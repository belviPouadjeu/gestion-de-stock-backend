package com.belvinard.gestiondestock.responses;

import com.belvinard.gestiondestock.dtos.LigneVenteDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LigneVentResponse {
    List<LigneVenteDTO> content;
}
