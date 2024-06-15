package br.jus.stj.skeletoApi.repos;

import br.jus.stj.skeletoApi.domain.Estado;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EstadoRepository extends JpaRepository<Estado, String> {

    boolean existsByUfIgnoreCase(String uf);

}
