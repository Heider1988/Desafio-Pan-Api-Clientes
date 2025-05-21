package com.api.rabbitmq.desafiopanapiclentes.infrastructure.exception;

import java.util.HashMap;
import java.util.Map;

public class ValidationException extends RuntimeException {
    
    private final Map<String, String> errors;
    
    public ValidationException(String message) {
        super(message);
        this.errors = new HashMap<>();
    }
    
    public ValidationException(String message, Map<String, String> errors) {
        super(message);
        this.errors = errors;
    }
    
    public Map<String, String> getErrors() {
        return errors;
    }
    
    public void addError(String field, String message) {
        this.errors.put(field, message);
    }
    
    public boolean hasErrors() {
        return !this.errors.isEmpty();
    }
}