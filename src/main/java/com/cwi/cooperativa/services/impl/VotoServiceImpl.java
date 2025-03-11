package com.cwi.cooperativa.services.impl;

import com.cwi.cooperativa.dtos.VotoDTO;
import com.cwi.cooperativa.entities.Associado;
import com.cwi.cooperativa.entities.Pauta;
import com.cwi.cooperativa.entities.Voto;
import com.cwi.cooperativa.repositories.VotoRepository;
import com.cwi.cooperativa.services.AssociadoService;
import com.cwi.cooperativa.services.PautaService;
import com.cwi.cooperativa.services.VotoService;
import com.cwi.cooperativa.services.exceptions.CooperativaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
public class VotoServiceImpl implements VotoService {

    @Autowired
    private VotoRepository votoRepository;

    @Autowired
    private PautaService pautaService;

    @Autowired
    private AssociadoService associadoService;

    @Override
    public void votar(Long idPauta, VotoDTO votoDTO) {
        Pauta pauta = pautaService.getPautaById(idPauta);
        pautaService.isPautaAberta(pauta);
        Associado associado = associadoService.findByCpf(votoDTO.getCpfAssociado());
        Voto votoRegistrado = votoRepository.findByPautaAndAssociado(pauta, associado);

        if(votoRegistrado != null){
            throw new CooperativaException(MessageFormat.format("Seu voto já foi registrado na pauta: {0}", pauta.getDescricao()));
        }

        Voto voto = new Voto();
        voto.setPauta(pauta);
        voto.setAssociado(associado);
        voto.setTipoVoto(votoDTO.getTipoVoto());
        votoRepository.save(voto);
    }
}
