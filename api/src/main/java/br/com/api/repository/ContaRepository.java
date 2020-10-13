package br.com.api.repository;

import br.com.api.model.Conta;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  ContaRepository  extends JpaRepository<Conta, Long> {

  public Optional<Conta> findByHashId(String hashId);
}
