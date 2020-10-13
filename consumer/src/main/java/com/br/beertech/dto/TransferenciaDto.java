package com.br.beertech.dto;

public class TransferenciaDto {

    private String conta;
    private Double valor;

    public TransferenciaDto() {
    }

    public TransferenciaDto(String conta, Double valor) {
        this.conta = conta;
        this.valor = valor;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
