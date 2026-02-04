package com.devsantana.lyday.shared.exeption;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApiError {

    // Data e hora que o erro ocorreu
    private LocalDateTime timestamp;
    // Código http (400, 404, 500, etc)
    private int status;
    // Nome do erro Http( Bad request, Not Found, etc.)
    private String error;
    // mensagem explicando o erro
    private String message;
    // Endpoint que gerou o erro
    private String path;
}
