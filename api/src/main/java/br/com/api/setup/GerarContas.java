package br.com.api.setup;

import br.com.api.model.Conta;
import br.com.api.repository.ContaRepository;
import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class GerarContas {


    private final ContaRepository contaRepository;

    @Autowired
    public GerarContas(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    public Conta criar() {
        Conta conta = new Conta();
        conta.setSaldo(1000d);
        conta = contaRepository.saveAndFlush(conta);
        conta.setHashId(Hashing.sha256()
            .hashString(conta.getId().toString()+"salt", StandardCharsets.UTF_8)
            .toString());
        return contaRepository.save(conta);
    }
}
