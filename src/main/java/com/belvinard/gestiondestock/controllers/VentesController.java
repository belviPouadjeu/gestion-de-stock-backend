package com.belvinard.gestiondestock.controllers;

import com.belvinard.gestiondestock.dtos.VenteDTO;
import com.belvinard.gestiondestock.responses.VenteResponse;
import com.belvinard.gestiondestock.services.VenteService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ventes")  // Ensure base mapping exists
@Tag(name = "Ventes API", description = "Operations related to Ventes")
public class VentesController {

    @Autowired
    private VenteService venteService;

    public VentesController(VenteService venteService) {
        this.venteService = venteService;
    }

    @GetMapping("/all")
    @Operation(summary = "Retrieve all ventes", description = "Returns a list of all ventes")
    public ResponseEntity<VenteResponse> getAllVentes() {
        VenteResponse venteResponse = venteService.getAllVentes();

        return new ResponseEntity<>(venteResponse, HttpStatus.OK);
    }

    @PostMapping("/entreprise/{entrepriseId}/createVentes")
    @Operation(summary = "Créer une vente", description = "Enregistre une nouvelle vente pour une entreprise")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vente créée avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public ResponseEntity<VenteDTO> createVente(@RequestBody VenteDTO venteDTO, @PathVariable Long entrepriseId){
        VenteDTO savedVente = venteService.createVente(entrepriseId, venteDTO);
        return new ResponseEntity<>(savedVente, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete a Vente by ID", description = "Deletes a vente and returns the deleted object")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vente successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Vente not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<VenteDTO> deleteVente(@PathVariable Long id) {
        VenteDTO deletedVente = venteService.deleteVente(id);
        return new ResponseEntity<>(deletedVente, HttpStatus.OK);
    }

    @Operation(summary = "Get a Vente by ID", description = "Retrieves a vente by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vente found"),
            @ApiResponse(responseCode = "404", description = "Vente not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<VenteDTO> getVenteById(@PathVariable Long id) {
        VenteDTO venteDTO = venteService.findVenteById(id);
        return ResponseEntity.ok(venteDTO);
    }

}

