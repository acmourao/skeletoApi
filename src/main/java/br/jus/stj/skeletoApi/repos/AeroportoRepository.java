package br.jus.stj.skeletoApi.repos;

import br.jus.stj.skeletoApi.domain.Aeroporto;
import br.jus.stj.skeletoApi.domain.Municipio;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AeroportoRepository extends JpaRepository<Aeroporto, Integer> {

    Aeroporto findFirstByMunicipio(Municipio municipio);

    boolean existsBySiglaIgnoreCase(String sigla);

}
