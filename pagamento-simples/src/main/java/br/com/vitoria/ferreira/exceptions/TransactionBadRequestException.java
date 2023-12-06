package br.com.vitoria.ferreira.exceptions;

public class TransactionBadRequestException extends RuntimeException {
    public TransactionBadRequestException(String message) {
        super(message);
    }
}
