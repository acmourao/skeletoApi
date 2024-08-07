package br.jus.stj.skeletoApi.service;

import br.jus.stj.skeletoApi.domain.Estado;
import br.jus.stj.skeletoApi.domain.Municipio;
import br.jus.stj.skeletoApi.model.EstadoDTO;
import br.jus.stj.skeletoApi.repos.EstadoRepository;
import br.jus.stj.skeletoApi.repos.MunicipioRepository;
import br.jus.stj.skeletoApi.util.NotFoundException;
import br.jus.stj.skeletoApi.util.ReferencedWarning;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class EstadoService {

    private final EstadoRepository estadoRepository;
    private final MunicipioRepository municipioRepository;

    public EstadoService(final EstadoRepository estadoRepository,
                         final MunicipioRepository municipioRepository) {
        this.estadoRepository = estadoRepository;
        this.municipioRepository = municipioRepository;
    }

    public List<Estado> findAll() {
        return estadoRepository.findAll(Sort.by("uf"));
    }

    public Estado get(final String uf) {
        return estadoRepository.findById(uf).orElseThrow(NotFoundException::new);
    }

    public String create(final EstadoDTO estadoDTO) {
        final Estado estado = new Estado();
        mapToEntity(estadoDTO, estado);
        estado.setUf(estadoDTO.getUf());
        return estadoRepository.save(estado).getUf();
    }

    public void update(final String uf, final EstadoDTO estadoDTO) {
        final Estado estado = estadoRepository.findById(uf)
                .orElseThrow(NotFoundException::new);
        mapToEntity(estadoDTO, estado);
        estadoRepository.save(estado);
    }

    public void delete(final String uf) {
        estadoRepository.deleteById(uf);
    }

    private EstadoDTO mapToDTO(final Estado estado, final EstadoDTO estadoDTO) {
        estadoDTO.setUf(estado.getUf());
        estadoDTO.setName(estado.getName());
        estadoDTO.setGentilic(estado.getGentilic());
        estadoDTO.setGentilicAlternative(estado.getGentilicAlternative());
        estadoDTO.setMacroregion(estado.getMacroregion());
        estadoDTO.setWebsite(estado.getWebsite());
        estadoDTO.setTimezone(estado.getTimezone());
        estadoDTO.setFlagImage(estado.getFlagImage());
        return estadoDTO;
    }

    private Estado mapToEntity(final EstadoDTO estadoDTO, final Estado estado) {
        estado.setName(estadoDTO.getName());
        estado.setGentilic(estadoDTO.getGentilic());
        estado.setGentilicAlternative(estadoDTO.getGentilicAlternative());
        estado.setMacroregion(estadoDTO.getMacroregion());
        estado.setWebsite(estadoDTO.getWebsite());
        estado.setTimezone(estadoDTO.getTimezone());
        estado.setFlagImage(estadoDTO.getFlagImage());
        return estado;
    }

    public boolean ufExists(final String uf) {
        return estadoRepository.existsByUfIgnoreCase(uf);
    }

    public ReferencedWarning getReferencedWarning(final String uf) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Estado estado = estadoRepository.findById(uf)
                .orElseThrow(NotFoundException::new);
        final Municipio ufMunicipio = municipioRepository.findFirstByUf(estado);
        if (ufMunicipio != null) {
            referencedWarning.setKey("estado.municipio.uf.referenced");
            referencedWarning.addParam(ufMunicipio.getId());
            return referencedWarning;
        }
        return null;
    }

}
