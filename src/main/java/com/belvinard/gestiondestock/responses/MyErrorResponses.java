package com.belvinard.gestiondestock.responses;

import lombok.Data;

import java.util.Map;

@Data
public class MyErrorResponses {
    private String status;
    private String message;
    private Map<String, String> errors;

    public MyErrorResponses() {
    }

    public MyErrorResponses(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public MyErrorResponses(String status, String message, Map<String, String> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    // Getters and setters
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Map<String, String> getErrors() { return errors; }
    public void setErrors(Map<String, String> errors) { this.errors = errors; }
}
