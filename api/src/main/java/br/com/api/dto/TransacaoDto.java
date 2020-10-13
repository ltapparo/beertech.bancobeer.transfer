package br.com.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoDto {

    private String operacao;
    @Min(value = 0, message = "valor inválido!")
    private Double valor;

}
