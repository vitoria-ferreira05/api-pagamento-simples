package br.com.vitoria.ferreira.controller.request;

import br.com.vitoria.ferreira.exceptions.TransactionException;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {

    private UUID id;

    @NotNull
    private Double amount;

    private String status;

}
