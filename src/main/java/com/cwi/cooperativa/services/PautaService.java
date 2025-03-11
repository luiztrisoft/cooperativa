package com.cwi.cooperativa.services;

import com.cwi.cooperativa.dtos.PautaDTO;
import com.cwi.cooperativa.dtos.ResultadoVotacaoDTO;
import com.cwi.cooperativa.entities.Pauta;

import java.util.List;

public interface PautaService {
    PautaDTO salvar(PautaDTO pautaDTO);
    List<PautaDTO> listar();
    PautaDTO buscar (Long id);
    PautaDTO abrirVotacao(Long id);
    Pauta getPautaById(Long id);
    boolean isPautaAberta(Pauta pauta);
    ResultadoVotacaoDTO contabilizarVotos(Long id);
}
