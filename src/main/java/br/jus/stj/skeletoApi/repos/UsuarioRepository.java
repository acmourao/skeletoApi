package br.jus.stj.skeletoApi.repos;

import br.jus.stj.skeletoApi.domain.Banco;
import br.jus.stj.skeletoApi.domain.Municipio;
import br.jus.stj.skeletoApi.domain.Usuario;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Query("SELECT u FROM Usuario u ORDER BY u.nome ASC LIMIT 20")
    List<Usuario> findByOrderByNomeAsc();

    Usuario findFirstByBanco(Banco banco);

    Usuario findFirstByDomicilio(Municipio municipio);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByBancoId(Integer id);

    boolean existsByDomicilioId(Integer id);

}
