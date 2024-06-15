package br.jus.stj.skeletoApi.rest;

import br.jus.stj.skeletoApi.domain.Aeroporto;
import br.jus.stj.skeletoApi.model.AeroportoDTO;
import br.jus.stj.skeletoApi.service.AeroportoService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/aeroportos", produces = MediaType.APPLICATION_JSON_VALUE)
public class AeroportoResource {

    private final AeroportoService aeroportoService;

    public AeroportoResource(final AeroportoService aeroportoService) {
        this.aeroportoService = aeroportoService;
    }

    @GetMapping
    public ResponseEntity<List<Aeroporto>> getAllAeroportos() {
        return ResponseEntity.ok(aeroportoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aeroporto> getAeroporto(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(aeroportoService.get(id));
    }

    @PostMapping
    public ResponseEntity<Integer> createAeroporto(
            @RequestBody @Valid final AeroportoDTO aeroportoDTO) {
        final Integer createdId = aeroportoService.create(aeroportoDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateAeroporto(@PathVariable(name = "id") final Integer id,
                                                   @RequestBody @Valid final AeroportoDTO aeroportoDTO) {
        aeroportoService.update(id, aeroportoDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAeroporto(@PathVariable(name = "id") final Integer id) {
        aeroportoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
