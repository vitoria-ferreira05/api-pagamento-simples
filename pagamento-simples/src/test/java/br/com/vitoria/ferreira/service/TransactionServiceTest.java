package br.com.vitoria.ferreira.service;

import br.com.vitoria.ferreira.controller.request.TransactionRequest;
import br.com.vitoria.ferreira.exceptions.TransactionException;
import br.com.vitoria.ferreira.model.Transaction;
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
    void deveProcessarPagamentoComSucesso() {
        Transaction transaction = new Transaction();
        transaction.setId(UUID.randomUUID());
        transaction.setStatus("Pendente");

        when(transactionRepository.findById(transaction.getId())).thenReturn(Optional.of(transaction));
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        String result = transactionService.processPayment(transaction.getId());

        verify(transactionRepository, times(1)).save(transaction);

        assertEquals("Pagamento processado com sucesso", result);
    }

    @Test
    void deveRetornarMensagemParaTransacaoInvalidaOuJaProcessada() {
        Transaction transaction = new Transaction();
        transaction.setId(UUID.randomUUID());
        transaction.setStatus("Pago");

        when(transactionRepository.findById(transaction.getId())).thenReturn(Optional.of(transaction));

        String result = transactionService.processPayment(transaction.getId());

        verify(transactionRepository, never()).save(transaction);

        assertEquals("Transação inválida ou já processada", result);
    }

    @Test
    void deveRetornarMensagemParaTransacaoNaoEncontrada() {
        UUID invalidTransactionId = UUID.randomUUID();

        when(transactionRepository.findById(invalidTransactionId)).thenReturn(Optional.empty());

        String result = transactionService.processPayment(invalidTransactionId);

        verify(transactionRepository, never()).save(any());

        assertEquals("Transação inválida ou já processada", result);
    }
}