package br.com.api.service;

import br.com.api.dto.TransacaoDto;
import br.com.api.dto.TransferenciaDto;
import br.com.api.model.Conta;
import br.com.api.model.Transacao;
import br.com.api.model.Transacao.Operacao;
import br.com.api.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import br.com.api.repository.ContaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class ContaService {

    private final ContaRepository contaRepository;
    private final TransacaoRepository transacaoRepository;

    @Autowired
    public ContaService(ContaRepository contaRepository, TransacaoRepository transacaoRepository) {
        this.contaRepository = contaRepository;
        this.transacaoRepository = transacaoRepository;
    }

    public List<Conta> listAll() {
        return contaRepository.findAll();
    }

    public Conta findByHashId(String hashId) {
        return contaRepository.findByHashId(hashId).orElseThrow();
    }

    public Double getSaldo(String hashId) {
        Conta conta = contaRepository.findByHashId(hashId).orElseThrow(() -> new RuntimeException("Not Found"));
        return conta.getSaldo();
    }

    public Conta saveOperacao(TransacaoDto request, String hashId) {

        Conta conta = contaRepository.findByHashId(hashId).orElseThrow(() -> new RuntimeException("Not Found"));

        Double valor= request.getValor();

        Transacao.Operacao operacao = Transacao.Operacao.valueOf(request.getOperacao().toUpperCase());

        if(operacao == Transacao.Operacao.SAQUE) {
            if (conta.getSaldo() < valor) throw new RuntimeException("Saldo insuficiente");
            conta.saque(valor);
        }

        if (operacao == Transacao.Operacao.DEPOSITO) conta.deposito(valor);

        Transacao transacao= Transacao
                .builder()
                .conta(conta)
                .operacao(operacao)
                .valor(request.getValor())
                .dataOperacao(LocalDateTime.now())
                .build();

        conta.getTransacao().add(transacao);
        transacaoRepository.save(transacao);
        return contaRepository.save(conta);
    }

    public Conta saveTransferencia(TransferenciaDto request, String hashId) {
        try{
            Conta conta = saveOperacao(new TransacaoDto(Operacao.SAQUE.name(),request.getValor()),hashId);
            saveOperacao(new TransacaoDto(Operacao.DEPOSITO.name(), request.getValor()),hashId);
            return  conta;
        }catch (RuntimeException e){
            throw new RuntimeException("Erro ao efetuar transferencia");
        }
    }
}
