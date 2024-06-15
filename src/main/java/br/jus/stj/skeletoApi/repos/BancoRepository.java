package br.jus.stj.skeletoApi.repos;

import br.jus.stj.skeletoApi.domain.Banco;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BancoRepository extends JpaRepository<Banco, Integer> {

    boolean existsByCompeIgnoreCase(String compe);

    boolean existsByCnpjIgnoreCase(String cnpj);

}
