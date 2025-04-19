package com.belvinard.gestiondestock.exceptions;

public class LigneCommandeNotFoundException extends RuntimeException {
    public LigneCommandeNotFoundException(String message) {
        super(message);
    }
}