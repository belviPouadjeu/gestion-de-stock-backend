package com.belvinard.gestiondestock;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Operation(summary = "Retourne un message 'Hello World'")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Succès : message renvoyé"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

}
