package br.com.api.dto;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class TransacaoDto {

    private String operacao;
    @Min(value = 0, message = "valor inválido!")
    private Double valor;

}
