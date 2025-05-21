package com.api.desafiopanapiclentes.infrastructure.exception;

import com.api.desafiopanapiclentes.infrastructure.response.ApiResponseWrapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiResponseWrapper<Object>> handleResourceNotFoundException(
            ResourceNotFoundException ex, HttpServletRequest request) {

        log.error("Recurso não encontrado: {}", ex.getMessage());

        ApiResponseWrapper<Object> response = ApiResponseWrapper.error(ex);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiResponseWrapper<Object>> handleEntityNotFoundException(
            EntityNotFoundException ex, HttpServletRequest request) {

        log.error("Entidade não encontrada: {}", ex.getMessage());

        ApiResponseWrapper<Object> response = ApiResponseWrapper.error(ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponseWrapper<Object>> handleValidationException(
            ValidationException ex, HttpServletRequest request) {

        log.error("Erro de validação: {}", ex.getMessage());

        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());

        if (ex.hasErrors()) {
            ex.getErrors().forEach((field, message) ->
                    errors.add(field + ": " + message)
            );
        }

        ApiResponseWrapper<Object> response = ApiResponseWrapper.error(errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponseWrapper<Object>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        log.error("Erro de validação de argumentos: {}", ex.getMessage());

        List<String> errors = new ArrayList<>();
        errors.add("Erro de validação nos dados da requisição");

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.add(fieldName + ": " + errorMessage);
        });

        ApiResponseWrapper<Object> response = ApiResponseWrapper.error(errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponseWrapper<Object>> handleConstraintViolationException(
            ConstraintViolationException ex, HttpServletRequest request) {

        log.error("Erro de violação de restrição: {}", ex.getMessage());

        List<String> errors = new ArrayList<>();
        errors.add("Erro de validação nos dados da requisição");

        ex.getConstraintViolations().forEach(violation -> {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            errors.add(fieldName + ": " + errorMessage);
        });

        ApiResponseWrapper<Object> response = ApiResponseWrapper.error(errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponseWrapper<Object>> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex, HttpServletRequest request) {

        log.error("Erro de tipo de argumento: {}", ex.getMessage());

        String message = String.format("O parâmetro '%s' com valor '%s' não pôde ser convertido para o tipo '%s'",
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

        ApiResponseWrapper<Object> response = ApiResponseWrapper.error(message);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiResponseWrapper<Object>> handleGenericException(
            Exception ex, HttpServletRequest request) {

        log.error("Erro interno do servidor: {}", ex.getMessage(), ex);

        ApiResponseWrapper<Object> response = ApiResponseWrapper.error(
                "Ocorreu um erro interno no servidor. Por favor, tente novamente mais tarde.");

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
