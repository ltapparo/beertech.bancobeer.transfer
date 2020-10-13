package br.com.api.dto;

import javax.validation.constraints.Min;
import lombok.Data;

@Data
public class TransferenciaDto {

    private String conta;
    @Min(value = 0, message = "valor inválido!")
    private Double valor;

}
