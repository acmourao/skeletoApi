package br.jus.stj.skeletoApi.repos;

import br.jus.stj.skeletoApi.domain.Estado;
import br.jus.stj.skeletoApi.domain.Municipio;
import br.jus.stj.skeletoApi.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface MunicipioRepository extends JpaRepository<Municipio, Integer> {

    Municipio findFirstByUf(Estado estado);

    boolean existsByMunicipioIgnoreCase(String municipio);

    List<Municipio> findByMunicipioContainingIgnoreCaseOrderByMunicipio(String cidade);

    @Query("SELECT m FROM Municipio m ORDER BY m.municipio ASC LIMIT 20")
    List<Municipio> findByOrderByMunicipioAsc();
}
