package service;

import Dto.TransacaoDto;
import model.Conta;
import model.Transacao;
import org.springframework.beans.factory.annotation.Autowired;
import repository.ContaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ContaService {

    //@Autowired
    //ContaRepository repository;

    ContaRepository contaRepository;

    @Autowired
    public ContaService(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    public List<Conta> listAll() {
        return contaRepository.findAll();
    }

    public Conta findById(String idConta) {
        return contaRepository.findById(idConta).orElseThrow();
    }

    public Double getSaldo(String idConta) {
        Conta conta = contaRepository.findById(idConta).orElseThrow(() -> new RuntimeException("Not Found"));

        return conta.getSaldo();
    }

    public void save(TransacaoDto request, String idConta) {

        Conta conta = contaRepository.findById(idConta).orElseThrow(() -> new RuntimeException("Not Found"));

        Double valor= request.getValor();

        if(request.getOperacao() ==Transacao.Operacao.SAQUE) ){
            valor = valor *-1;
        }

        Transacao transacao= Transacao
                .builder()
                .conta(conta)
                .operacao(request.getOperacao())
                .valor(request.getValor())
                .dataOperacao(LocalDateTime.now())
                .build();

        conta.getTransacao().add(transacao);
        conta.setSaldo(conta.getSaldo() + valor);

        contaRepository.save(conta);


    }
}
