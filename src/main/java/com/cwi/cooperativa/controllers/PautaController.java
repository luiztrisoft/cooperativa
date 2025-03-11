package com.cwi.cooperativa.controllers;

import com.cwi.cooperativa.dtos.PautaDTO;
import com.cwi.cooperativa.dtos.ResultadoVotacaoDTO;
import com.cwi.cooperativa.dtos.VotoDTO;
import com.cwi.cooperativa.services.PautaService;
import com.cwi.cooperativa.services.VotoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pautas")
public class PautaController {

    @Autowired
    private PautaService pautaService;

    @Autowired
    private VotoService votoService;

    @PostMapping
    public ResponseEntity<PautaDTO> salvar(@Valid @RequestBody PautaDTO pautaDTO){
        PautaDTO dto = pautaService.salvar(pautaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PostMapping("/{id}/votar")
    public ResponseEntity<Void> votar(@PathVariable Long id, @RequestBody VotoDTO votoDTO) {
        votoService.votar(id, votoDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/abrir-votacao")
    public ResponseEntity<PautaDTO> abrirVotacao(@PathVariable("id") Long id){
        PautaDTO dto = pautaService.abrirVotacao(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping
    public ResponseEntity<List<PautaDTO>> listar(){
        return ResponseEntity.status(HttpStatus.OK).body(pautaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PautaDTO> buscar(@PathVariable("id") Long id){
       return ResponseEntity.status(HttpStatus.OK).body(pautaService.buscar(id));
    }

    @GetMapping("/{id}/contabilizar-votos")
    public ResponseEntity<ResultadoVotacaoDTO> contabilizarVotos(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(pautaService.contabilizarVotos(id));
    }
}
