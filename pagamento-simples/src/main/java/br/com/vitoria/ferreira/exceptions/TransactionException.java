package br.com.vitoria.ferreira.exceptions;

public class TransactionException extends RuntimeException {
    public TransactionException(String message) {
        super(message);
    }
}
