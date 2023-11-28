package br.com.vitoria.ferreira.service;

import br.com.vitoria.ferreira.controller.request.TransactionRequest;
import br.com.vitoria.ferreira.exceptions.TransactionException;
import br.com.vitoria.ferreira.model.Transaction;
import br.com.vitoria.ferreira.model.enums.Status;
import br.com.vitoria.ferreira.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction startTransaction(TransactionRequest transactionRequest) throws TransactionException {

        Transaction transaction = new Transaction();

        if (transactionRequest.getAmount() == null) {
            throw new TransactionException("O valor da transação não pode ser nulo.");
        }

        if (transactionRequest.getAmount() <= 0) {
            throw new TransactionException("O valor da transação deve ser maior que zero.");
        }

        transaction.setAmount(transactionRequest.getAmount());
        transaction.setStatus(Status.PENDENTE);

        return transactionRepository.save(transaction);
    }

    public Transaction processPayment(UUID id) throws TransactionException {
        Transaction existingTransaction = transactionRepository.findById(id).orElseThrow(() -> new TransactionException("Transação inválida ou já processada"));
        existingTransaction.setStatus(Status.PAGO);
        return transactionRepository.save(existingTransaction);
    }
}
