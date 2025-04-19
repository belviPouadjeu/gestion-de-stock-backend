package com.belvinard.gestiondestock.exceptions;

public class CommandeFournisseurNotFoundException extends RuntimeException {
    public CommandeFournisseurNotFoundException(String message) {
        super(message);
    }
}