package br.com.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Entity
@Data
public class Conta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Double saldo;

    @OneToMany(mappedBy =  "transacao")
    @JsonIgnore
    private List<Transacao> transacao;

    public Double getSaldo() {
        return Optional.ofNullable(saldo).orElse(0d);
    }

    public void saque(Double valor) {
        this.setSaldo(this.saldo + valor *-1);
    }

    public void deposito(Double valor) {
        this.setSaldo(this.saldo + valor);
    }

}
