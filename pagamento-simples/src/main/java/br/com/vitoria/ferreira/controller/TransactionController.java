package br.com.vitoria.ferreira.controller;

import br.com.vitoria.ferreira.controller.request.TransactionRequest;
import br.com.vitoria.ferreira.exceptions.TransactionException;
import br.com.vitoria.ferreira.model.Transaction;
import br.com.vitoria.ferreira.service.TransactionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/transaction")
@Tag(name = "Transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public Transaction startTransaction(@RequestBody TransactionRequest transactionRequest) throws TransactionException {
        return transactionService.startTransaction(transactionRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaction> processPayment(@PathVariable UUID id) throws TransactionException {
        Transaction updadteTransaction = transactionService.processPayment(id);
        return new ResponseEntity<>(updadteTransaction, HttpStatus.OK);
    }
}
