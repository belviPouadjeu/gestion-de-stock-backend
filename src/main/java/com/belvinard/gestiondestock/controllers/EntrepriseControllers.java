package com.belvinard.gestiondestock.controllers;

import com.belvinard.gestiondestock.dtos.EntrepriseDTO;
import com.belvinard.gestiondestock.exceptions.APIException;
import com.belvinard.gestiondestock.exceptions.ResourceNotFoundException;
import com.belvinard.gestiondestock.responses.EntrepriseResponse;
import com.belvinard.gestiondestock.responses.ErrorResponse;
import com.belvinard.gestiondestock.services.EntrepriseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/public/entreprises")
@Tag(name = "Entreprise", description = "API pour la gestion des entreprises")
public class EntrepriseControllers {
    public final EntrepriseService entrepriseService;

    public EntrepriseControllers(EntrepriseService entrepriseService) {
        this.entrepriseService = entrepriseService;
    }

    @Operation(
            summary = "Ajouter une entreprise",
            description = "Ajoute une nouvelle entreprise à la base de données"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Entreprise créée avec succès",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EntrepriseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Requête invalide", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur", content = @Content)
    })
    @PostMapping
    public ResponseEntity<EntrepriseDTO> createEntreprise(@Valid @RequestBody EntrepriseDTO entrepriseDTO) {
        EntrepriseDTO savedEntrepriseDTO = entrepriseService.createEntreprise(entrepriseDTO);
        return new ResponseEntity<>(savedEntrepriseDTO, HttpStatus.CREATED);
    }

    // ==================== GET ALL ARTICLE
    @Operation(
            summary = "Retourne la liste des entreprise",
            description = """
       Retourne la liste des categories""
    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of entreprise successfully retrieved",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EntrepriseResponse.class),
                            examples = @ExampleObject(value = """
{
    "content": [
        {
             "id": 6,
             "nom": "S",
             "description": "Entreprise de BTP et génie civil",
             "adresse": {
             "adresse1": "Rue Joseph Clerc",
             "adresse2": "Bonanjo",
             "ville": "Douala",
             "codePostale": "4015",
             "pays": "Cameroun"
             },
                "codeFiscal": "CM001234567",
                "photo": "sogeasatom-logo.png",
                "email": "contact@sogeasatom.cm",
                "numTel": "+237 233 42 40 42",
                "steWeb": "https://www.sogeasatom.cm"
         }
    ]
}
                        """)
                    )
            )
    })
    @GetMapping
    public ResponseEntity<EntrepriseResponse> getAllEntreprises(){
        EntrepriseResponse entreprises = entrepriseService.
                getAllEntreprises();
        return new ResponseEntity<>(entreprises, HttpStatus.OK);
    }

    @Operation(summary = "Récupère une entreprise par son ID", description = "Cette méthode permet de récupérer une entreprise en fonction de son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entreprise trouvée avec succès"),
            @ApiResponse(responseCode = "404", description = "Entreprise non trouvée"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntrepriseDTO> getEntrepriseById(
            @Parameter(description = "ID de l'entreprise à récupérer", required = true) @PathVariable Long id) {
        EntrepriseDTO entreprise = entrepriseService.findEntrepriseById(id);
        return ResponseEntity.ok(entreprise);
    }



//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
//        ErrorResponse errorResponse = new ErrorResponse("NOT_FOUND", ex.getMessage());
//        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(APIException.class)
//    public ResponseEntity<ErrorResponse> myAPIException(APIException ex) {
//        ErrorResponse errorResponse = new ErrorResponse("BAD_REQUEST", ex.getMessage());
//
//        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//    }
//
//    // ✅ Handle validation errors
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
//        Map<String, String> errors = new HashMap<>();
//        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
//            errors.put(error.getField(), error.getDefaultMessage());  // Collect field errors
//        }
//
//        ErrorResponse errorResponse = new ErrorResponse(
//                "BAD_REQUEST",
//                "Validation failed. Please correct the errors.",
//                errors
//        );
//
//        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//    }


}
