package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

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



}
