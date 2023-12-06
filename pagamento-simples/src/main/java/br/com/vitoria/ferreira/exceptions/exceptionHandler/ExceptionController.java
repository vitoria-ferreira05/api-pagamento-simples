package br.com.vitoria.ferreira.exceptions.exceptionHandler;

import br.com.vitoria.ferreira.exceptions.TransactionException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<Object> handlerTransactionNotFound(TransactionException transactionException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(transactionException.getMessage());
    }
}
