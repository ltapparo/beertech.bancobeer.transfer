package br.com.api;

import br.com.api.dto.TransacaoDto;
import br.com.api.model.Conta;
import br.com.api.model.Transacao;
import br.com.api.service.ContaService;
import br.com.api.setup.GerarContas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GetSaldoTest {

    @Autowired
    private ContaService contaService;

    @Autowired
    private GerarContas gerarContas;

    @Nested
    public abstract class SetupGetSaldo {

        Conta conta;
        Double saldo;

        @BeforeEach
        void setup() {
            conta = gerarContas.criar();
            setupConta();
            saldo = contaService.getSaldo(conta.getHashId());
        }

        protected void setupConta() {
            TransacaoDto transacaoDto = new TransacaoDto();
            transacaoDto.setOperacao(Transacao.Operacao.SAQUE.name());
            transacaoDto.setValor(100D);

            TransacaoDto transacaoDto1 = new TransacaoDto();
            transacaoDto1.setOperacao(Transacao.Operacao.DEPOSITO.name());
            transacaoDto1.setValor(50d);

            contaService.saveOperacao(transacaoDto, conta.getHashId());
            contaService.saveOperacao(transacaoDto1, conta.getHashId());
        }
    }


    @Nested
    public class GetSaldo extends SetupGetSaldo {

        @Test
        void DeveRetornarSadoIgualA950() {
            assertThat(saldo).isEqualTo(950);
        }
    }


}
