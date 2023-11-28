package br.com.vitoria.ferreira.service;

import br.com.vitoria.ferreira.controller.request.TransactionRequest;
import br.com.vitoria.ferreira.exceptions.TransactionException;
import br.com.vitoria.ferreira.model.Transaction;
import br.com.vitoria.ferreira.model.enums.Status;
import br.com.vitoria.ferreira.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class TransactionServiceTest {
    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void deveRetornarTransacaoSalvaComSucesso() throws TransactionException {
        TransactionRequest validRequest = new TransactionRequest();
        validRequest.setAmount(100.0);

        when(transactionRepository.save(Mockito.any(Transaction.class))).thenReturn(new Transaction());

        Transaction result = transactionService.startTransaction(validRequest);

        verify(transactionRepository, times(1)).save(Mockito.any(Transaction.class));

    }

    @Test
    void deveRetornarExcecaoSeValorDaTransacaoForNulo() {
        TransactionRequest invalidRequest = new TransactionRequest();

        TransactionException exception = assertThrows(TransactionException.class, () -> {
            transactionService.startTransaction(invalidRequest);
        });

        assertEquals("O valor da transação não pode ser nulo.", exception.getMessage());
    }

    @Test
    void deveRetornarExcecaoSeValorForIgualZero() {
        TransactionRequest invalidRequest = new TransactionRequest();
        invalidRequest.setAmount(0.0);

        TransactionException exception = assertThrows(TransactionException.class, () -> {
            transactionService.startTransaction(invalidRequest);
        });

        assertEquals("O valor da transação deve ser maior que zero.", exception.getMessage());
    }

    @Test
    void deveRetornarExcecaoSeValorForMenorQueZero() {
        TransactionRequest invalidRequest = new TransactionRequest();
        invalidRequest.setAmount(-1.0);

        TransactionException exception = assertThrows(TransactionException.class, () -> {
            transactionService.startTransaction(invalidRequest);
        });

        assertEquals("O valor da transação deve ser maior que zero.", exception.getMessage());
    }

    @Test
    void deveProcessarPagamentoComSucesso() throws TransactionException {
        Transaction transaction = new Transaction();
        transaction.setId(UUID.randomUUID());
        transaction.setStatus(Status.PENDENTE);

        when(transactionRepository.findById(transaction.getId())).thenReturn(Optional.of(transaction));
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        Transaction result = transactionService.processPayment(transaction.getId());

        verify(transactionRepository, times(1)).save(transaction);

        assertEquals(Status.PAGO, result.getStatus());
    }
}