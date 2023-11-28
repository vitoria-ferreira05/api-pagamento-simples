package br.com.vitoria.ferreira.controller.request;

import br.com.vitoria.ferreira.model.enums.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {

    @NotNull
    @JsonProperty(value = "valor")
    private Double amount;

    private Status status;

}
