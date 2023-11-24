package br.com.vitoria.ferreira.controller;

import br.com.vitoria.ferreira.controller.request.TransactionRequest;
import br.com.vitoria.ferreira.exceptions.TransactionException;
import br.com.vitoria.ferreira.model.Transaction;
import br.com.vitoria.ferreira.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    @Test
    void deveRegistrarValorDaTransacaoComSucesso() throws TransactionException {
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setAmount(10.0);

        Transaction expectedTransaction = new Transaction();
        expectedTransaction.setId(UUID.fromString("c60ab987-a80f-47c4-9e16-c782fb59167"));
        expectedTransaction.setStatus("Pendente");

        when(transactionService.startTransaction(transactionRequest)).thenReturn(expectedTransaction);

        Transaction actualTransaction = transactionController.startTransaction(transactionRequest);

        assertNotNull(actualTransaction);
        assertEquals(expectedTransaction.getId(), actualTransaction.getId());
        assertEquals(expectedTransaction.getStatus(), actualTransaction.getStatus());

        verify(transactionService, times(1)).startTransaction(transactionRequest);

    }
}