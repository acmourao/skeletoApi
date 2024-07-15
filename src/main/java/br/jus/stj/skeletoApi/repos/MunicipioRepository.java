package br.jus.stj.skeletoApi.repos;

import br.jus.stj.skeletoApi.domain.Estado;
import br.jus.stj.skeletoApi.domain.Municipio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MunicipioRepository extends JpaRepository<Municipio, Integer> {

    Municipio findFirstByUf(Estado estado);

    boolean existsByMunicipioIgnoreCase(String municipio);

    List<Municipio> findByMunicipioContainingIgnoreCaseOrderByMunicipio(String cidade);
}
