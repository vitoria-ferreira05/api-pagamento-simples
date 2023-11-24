package br.com.vitoria.ferreira.controller;

import br.com.vitoria.ferreira.controller.request.TransactionRequest;
import br.com.vitoria.ferreira.exceptions.TransactionException;
import br.com.vitoria.ferreira.model.Transaction;
import br.com.vitoria.ferreira.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/start")
    public Transaction startTransaction(@RequestBody TransactionRequest transactionRequest) throws TransactionException {
        return transactionService.startTransaction(transactionRequest);
    }

    @PostMapping("/process")
    public ResponseEntity<?> processPayment(@RequestBody TransactionRequest transactionRequest) {
        UUID id = transactionRequest.getId();
        String result = transactionService.processPayment(id);
        HttpStatus status = result.contains("sucesso") ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(result, status);
    }
}
