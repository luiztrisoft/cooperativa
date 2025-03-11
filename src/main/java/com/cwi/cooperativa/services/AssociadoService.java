package com.cwi.cooperativa.services;

import com.cwi.cooperativa.dtos.AssociadoDTO;
import com.cwi.cooperativa.entities.Associado;

import java.util.List;

public interface AssociadoService {
    AssociadoDTO salvar(AssociadoDTO associadoDTO);
    List<AssociadoDTO> listar();
    AssociadoDTO buscar(Long id);
    Associado findByCpf(String cpf);
}
