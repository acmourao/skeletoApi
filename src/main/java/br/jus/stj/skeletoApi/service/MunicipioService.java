package br.jus.stj.skeletoApi.service;

import br.jus.stj.skeletoApi.domain.Aeroporto;
import br.jus.stj.skeletoApi.domain.Estado;
import br.jus.stj.skeletoApi.domain.Municipio;
import br.jus.stj.skeletoApi.domain.Usuario;
import br.jus.stj.skeletoApi.model.MunicipioDTO;
import br.jus.stj.skeletoApi.repos.AeroportoRepository;
import br.jus.stj.skeletoApi.repos.EstadoRepository;
import br.jus.stj.skeletoApi.repos.MunicipioRepository;
import br.jus.stj.skeletoApi.repos.UsuarioRepository;
import br.jus.stj.skeletoApi.util.NotFoundException;
import java.util.List;

import br.jus.stj.skeletoApi.util.ReferencedWarning;
import org.springframework.stereotype.Service;


@Service
public class MunicipioService {

    private final MunicipioRepository municipioRepository;
    private final EstadoRepository estadoRepository;
    private final AeroportoRepository aeroportoRepository;
    private final UsuarioRepository usuarioRepository;

    public MunicipioService(final MunicipioRepository municipioRepository,
                            final EstadoRepository estadoRepository, final AeroportoRepository aeroportoRepository,
                            final UsuarioRepository usuarioRepository) {
        this.municipioRepository = municipioRepository;
        this.estadoRepository = estadoRepository;
        this.aeroportoRepository = aeroportoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<MunicipioDTO> findAll() {
        return municipioRepository.findByOrderByMunicipioAsc().stream()
                .map(municipio -> mapToDTO(municipio, new MunicipioDTO()))
                .toList();
    }

    public List<Municipio> findByMunicipio(String municipio) {
        return municipioRepository.findByMunicipioContainingIgnoreCaseOrderByMunicipio(municipio)
                .stream()
                .limit(20)
                .toList();
    }

    public MunicipioDTO get(final Integer id) {
        return municipioRepository.findById(id)
                .map(municipio -> mapToDTO(municipio, new MunicipioDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final MunicipioDTO municipioDTO) {
        final Municipio municipio = new Municipio();
        mapToEntity(municipioDTO, municipio);
        return municipioRepository.save(municipio).getId();
    }

    public void update(final Integer id, final MunicipioDTO municipioDTO) {
        final Municipio municipio = municipioRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(municipioDTO, municipio);
        municipioRepository.save(municipio);
    }

    public void delete(final Integer id) {
        municipioRepository.deleteById(id);
    }

    private Municipio mapToEntity(final MunicipioDTO municipioDTO, final Municipio municipio) {
        municipio.setMunicipio(municipioDTO.getMunicipio());
        final Estado uf = municipioDTO.getUf() == null ? null : estadoRepository.findById(municipioDTO.getUf())
                .orElseThrow(NotFoundException::new);
        municipio.setUf(uf);
        return municipio;
    }

    public boolean municipioExists(final String municipio) {
        return municipioRepository.existsByMunicipioIgnoreCase(municipio);
    }

    private MunicipioDTO mapToDTO(Municipio municipio, MunicipioDTO municipioDTO) {
        municipioDTO.setId(municipio.getId());
        municipioDTO.setMunicipio(municipio.getMunicipio());
        municipioDTO.setUf(municipio.getUf().getUf());
        return municipioDTO;
    }

    public ReferencedWarning getReferencedWarning(final Integer id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Municipio municipio = municipioRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Aeroporto municipioAeroporto = aeroportoRepository.findFirstByMunicipio(municipio);
        if (municipioAeroporto != null) {
            referencedWarning.setKey("municipio.aeroporto.municipio.referenced");
            referencedWarning.addParam(municipioAeroporto.getId());
            return referencedWarning;
        }
        final Usuario domicilioUsuario = usuarioRepository.findFirstByDomicilio(municipio);
        if (domicilioUsuario != null) {
            referencedWarning.setKey("municipio.usuario.domicilio.referenced");
            referencedWarning.addParam(domicilioUsuario.getId());
            return referencedWarning;
        }
        return null;
    }

}
