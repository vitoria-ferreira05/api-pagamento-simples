package br.com.vitoria.ferreira.exceptions.exceptionHandler;

import br.com.vitoria.ferreira.exceptions.TransactionBadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BadRequestExceptionHandler {
    @ExceptionHandler(TransactionBadRequestException.class)
    public ResponseEntity<Object> handlerTransactionNotFound(TransactionBadRequestException transactionBadRequestException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(transactionBadRequestException.getMessage());
    }
}

