package com.devsantana.lyday.shared.exeption;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //===============================
    // ERRO 404 - ENTIDADE NÃO ENCONTRADA
    //===============================
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleEntityNotFound(
            EntityNotFoundException ex,
            HttpServletRequest request
    ){
        return buildError(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request.getRequestURI()
        );
    }
    //===============================
    // ERRO 400 - VALIDAÇÃO DE DTO
    //===============================
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationError(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ){
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error ->
                        error.getField() + ": " + error.getDefaultMessage()
                )
                .findFirst()
                .orElse("Erro de Validação");

        return buildError(
                HttpStatus.BAD_REQUEST,
                message,
                request.getRequestURI()
        );
    }
    //===============================
    // ERRO 400 - REGRA DE NEGÓCIO
    //===============================
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleBusinessRule(
            IllegalArgumentException ex,
            HttpServletRequest request
    ){
        return buildError(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request.getRequestURI()
        );
    }
    //===============================
    // ERRO 500 - ERRO GENÉRICO
    //===============================
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericError(
            Exception ex,
            HttpServletRequest request
    ){
        ex.printStackTrace();
        return buildError(
                HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno inesperado",
                request.getRequestURI()
        );
    }
    //===============================
    // METODO AUXILIAR PARA MONTAR O ERRO
    //===============================
    private ResponseEntity<ApiError> buildError(
            HttpStatus status,
            String message,
            String path
    ){
        ApiError error = new ApiError(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                path
        );
    return ResponseEntity
            .status(status)
            .body(error);
    }
}
