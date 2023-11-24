package br.com.vitoria.ferreira.service;

import br.com.vitoria.ferreira.controller.request.TransactionRequest;
import br.com.vitoria.ferreira.exceptions.TransactionException;
import br.com.vitoria.ferreira.model.Transaction;
import br.com.vitoria.ferreira.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
        if (transactionRequest == null) {
            throw new TransactionException("O valor de transação não pode ser null.");
        }

        Double amount = transactionRequest.getAmount();

        if (amount == null) {
            throw new TransactionException("O valor da transação não pode ser nulo.");
        }

        if (amount <= 0) {
            throw new TransactionException("O valor da transação deve ser maior que zero.");
        }

        Transaction transaction = new Transaction();
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setStatus("Pendente");

        try {
            return transactionRepository.save(transaction);
        } catch (DataAccessException e) {
            throw new TransactionException("Erro ao iniciar a transação.");
        }
    }

    public String processPayment(UUID id) {
        Transaction transaction = transactionRepository.findById(id).orElse(null);

        if (transaction == null || "Pago".equals(transaction.getStatus())) {
            return "Transação inválida ou já processada";
        }

        transaction.setStatus("Pago");
        transactionRepository.save(transaction);
        return "Pagamento processado com sucesso";
    }
}
