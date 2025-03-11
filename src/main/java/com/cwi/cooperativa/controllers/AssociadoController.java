package com.cwi.cooperativa.controllers;

import com.cwi.cooperativa.dtos.AssociadoDTO;
import com.cwi.cooperativa.entities.Associado;
import com.cwi.cooperativa.enums.TipoVoto;
import com.cwi.cooperativa.services.AssociadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/associados")
public class AssociadoController {

    @Autowired
    private AssociadoService associadoService;

    @PostMapping
    public ResponseEntity<Void> salvar(@Valid @RequestBody AssociadoDTO associadoDTO){
       AssociadoDTO dto = associadoService.salvar(associadoDTO);
       URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
       return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<List<AssociadoDTO>> listar(){
        return ResponseEntity.status(HttpStatus.OK).body(associadoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssociadoDTO> buscar(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(associadoService.buscar(id));
    }
}
