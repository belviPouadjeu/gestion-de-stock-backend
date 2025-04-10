package com.belvinard.gestiondestock.responses;

import com.belvinard.gestiondestock.dtos.EntrepriseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntrepriseResponse {
    List<EntrepriseDTO> content;
}
