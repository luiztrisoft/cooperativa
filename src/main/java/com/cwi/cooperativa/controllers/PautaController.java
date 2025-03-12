package com.cwi.cooperativa.controllers;

import com.cwi.cooperativa.dtos.PautaDTO;
import com.cwi.cooperativa.dtos.ResultadoVotacaoDTO;
import com.cwi.cooperativa.dtos.VotoDTO;
import com.cwi.cooperativa.services.PautaService;
import com.cwi.cooperativa.services.VotoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pautas")
@Tag(name = "PautaController", description = "Gerenciamento de pautas")
public class PautaController {

    private static final Logger logger = LoggerFactory.getLogger(PautaController.class);

    @Autowired
    private PautaService pautaService;

    @Autowired
    private VotoService votoService;

    @PostMapping(produces = "application/json")
    public ResponseEntity<PautaDTO> salvar(@Valid @RequestBody PautaDTO pautaDTO){
        logger.info("Recebida solicitação para salvar uma nova pauta: {}", pautaDTO);
        PautaDTO dto = pautaService.salvar(pautaDTO);
        logger.info("Pauta salva com sucesso: {}", dto.getDescricao());
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PostMapping("/{id}/votar")
    public ResponseEntity<Void> votar(@PathVariable Long id, @RequestBody VotoDTO votoDTO) {
        logger.info("Recebido voto para a pauta com ID {}: {}", id, votoDTO);
        votoService.votar(id, votoDTO);
        logger.info("Voto registrado com sucesso para a pauta com ID {}", id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/abrir-votacao")
    public ResponseEntity<PautaDTO> abrirVotacao(@PathVariable("id") Long id){
        logger.info("Recebida solicitação para abrir votação para a pauta com ID {}", id);
        PautaDTO dto = pautaService.abrirVotacao(id);
        logger.info("Votação aberta com sucesso para a pauta com ID {}", id);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping
    @Cacheable(value = "pautas")
    public ResponseEntity<List<PautaDTO>> listar(){
        logger.info("Recebida solicitação para listar todas as pautas");
        List<PautaDTO> pautas = pautaService.listar();
        logger.info("Número de pautas encontradas: {}", pautas.size());
        return ResponseEntity.status(HttpStatus.OK).body(pautas);
    }

    @GetMapping("/{id}")
    @Cacheable(value = "pauta")
    public ResponseEntity<PautaDTO> buscar(@PathVariable("id") Long id){
        logger.info("Recebida solicitação para buscar a pauta com ID {}", id);
        PautaDTO dto = pautaService.buscar(id);
        logger.info("Pauta encontrada: {}", dto.getDescricao());
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @GetMapping("/{id}/contabilizar-votos")
    @Cacheable(value = "contabilizarVotos")
    public ResponseEntity<ResultadoVotacaoDTO> contabilizarVotos(@PathVariable("id") Long id){
        logger.info("Recebida solicitação para contabilizar votos da pauta com ID {}", id);
        ResultadoVotacaoDTO resultado = pautaService.contabilizarVotos(id);
        logger.info("Resultado da votação para a pauta com ID {}: {}", id, resultado);
        return ResponseEntity.status(HttpStatus.OK).body(resultado);
    }
}
