package repository;

import model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  ContaRepository  extends JpaRepository<Conta,String> {

}
