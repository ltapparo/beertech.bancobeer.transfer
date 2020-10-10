package model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@Builder
public class Transacao implements Serializable {

    public enum Operacao{DEPOSITO,SAQUE}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private double valor;

    @JoinTable(name = "tipo_operacao", joinColumns = @JoinColumn(name = "transacao_id"))
    @Enumerated(EnumType.STRING)
    private Operacao operacao;

    private LocalDateTime dataOperacao;

    @ManyToOne
    @JoinColumn(name = "conta_id" )
    private Conta conta;


}
