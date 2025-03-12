package com.cwi.cooperativa.controllers;

import com.cwi.cooperativa.dtos.AssociadoDTO;
import com.cwi.cooperativa.services.AssociadoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/associados")
@Tag(name = "AssociadoController", description = "Gerenciamento de associados")
public class AssociadoController {

    private static final Logger logger = LoggerFactory.getLogger(AssociadoController.class);

    @Autowired
    private AssociadoService associadoService;

    @PostMapping
    public ResponseEntity<Void> salvar(@Valid @RequestBody AssociadoDTO associadoDTO){
        logger.info("Recebida solicitação para salvar um novo associado: {}", associadoDTO);
        AssociadoDTO dto = associadoService.salvar(associadoDTO);
        logger.info("Associado salvo com sucesso: {}", dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    @Cacheable(value = "associados")
    public ResponseEntity<List<AssociadoDTO>> listar(){
        logger.info("Recebida solicitação para listar todos os associados");
        List<AssociadoDTO> associados = associadoService.listar();
        logger.info("Número de associados encontrados: {}", associados.size());
        return ResponseEntity.status(HttpStatus.OK).body(associados);
    }

    @GetMapping("/{id}")
    @Cacheable(value = "associado")
    public ResponseEntity<AssociadoDTO> buscar(@PathVariable("id") Long id){
        logger.info("Recebida solicitação para buscar o associado com ID {}", id);
        AssociadoDTO associado = associadoService.buscar(id);
        logger.info("Associado encontrado: {}", associado.getNome());
        return ResponseEntity.status(HttpStatus.OK).body(associado);
    }
}
