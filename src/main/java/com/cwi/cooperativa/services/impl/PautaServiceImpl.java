package com.cwi.cooperativa.services.impl;

import com.cwi.cooperativa.dtos.PautaDTO;
import com.cwi.cooperativa.dtos.ResultadoVotacaoDTO;
import com.cwi.cooperativa.entities.Pauta;
import com.cwi.cooperativa.entities.Voto;
import com.cwi.cooperativa.enums.StatusPauta;
import com.cwi.cooperativa.enums.TipoVoto;
import com.cwi.cooperativa.mapstruct.PautaMapper;
import com.cwi.cooperativa.repositories.PautaRepository;
import com.cwi.cooperativa.services.PautaService;
import com.cwi.cooperativa.services.exceptions.CooperativaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PautaServiceImpl implements PautaService {

    @Autowired
    private PautaRepository pautaRepository;

    @Autowired
    private PautaMapper pautaMapper;

    @Override
    public PautaDTO salvar(PautaDTO pautaDTO) {
        Pauta pauta = pautaMapper.toEntity(pautaDTO);
        verificarExistenciaPauta(pauta);

        if(pauta.getTempoVotacao() <= 60)
            pauta.setTempoVotacao(60);

        pauta.setStatusPauta(StatusPauta.FECHADA);
        return pautaMapper.toDTO(pautaRepository.save(pauta));
    }

    @Override
    public List<PautaDTO> listar() {
        return pautaMapper.toDTOlist(pautaRepository.findAll());
    }

    @Override
    public PautaDTO buscar(Long id) {
        return pautaMapper.toDTO(getPautaById(id));
    }

    @Override
    public PautaDTO abrirVotacao(Long id) {
        Pauta pauta = getPautaById(id);
        pauta.setStatusPauta(StatusPauta.ABERTA);
        pauta.setDataInicio(LocalDateTime.now());
        pauta.setDataFim(pauta.getDataInicio().plusSeconds(pauta.getTempoVotacao()));

        return pautaMapper.toDTO(pautaRepository.save(pauta));
    }

    @Override
    public Pauta getPautaById(Long id) {
        return pautaRepository.findById(id).orElseThrow(() -> new CooperativaException("Pauta não encontrada."));
    }

    @Override
    public boolean isPautaAberta(Pauta pauta){
        if (pauta.getDataInicio() == null || pauta.getDataFim() == null) {
            throw new CooperativaException("É necessário parametrizar o período da votação.");
        }

        LocalDateTime agora = LocalDateTime.now();
        if (pauta.getDataInicio().isBefore(agora)
                && pauta.getDataFim().isAfter(agora)
                && pauta.getStatusPauta() == StatusPauta.ABERTA) {
            return true;
        }

        throw new CooperativaException("Esta pauta está fora do período ou já foi encerrada.");
    }

    @Override
    public ResultadoVotacaoDTO contabilizarVotos(Long id) {
        Pauta pauta = getPautaById(id);
        List<Voto> votos = pauta.getVotos();

        ResultadoVotacaoDTO resultado = new ResultadoVotacaoDTO();
        resultado.setPautaVotacao(pauta.getDescricao());
        resultado.setTotalVotos(votos.size());
        resultado.setVotosSim(votos.stream().filter(v -> v.getTipoVoto() == TipoVoto.SIM).count());
        resultado.setVotosNao(votos.stream().filter(v -> v.getTipoVoto() == TipoVoto.NAO).count());
        return resultado;
    }

    @Scheduled(fixedRate = 60000 * 1 * 60)
    @Transactional
    public void fecharPautasExpiradas() {
        System.out.println("\nFechando pautas expiradas\n");
        pautaRepository.fecharPautasExpiradas(LocalDateTime.now());
    }

    private void verificarExistenciaPauta(Pauta pauta) {
       Optional<Pauta> pautaCadastrada = pautaRepository.findByDescricao(pauta.getDescricao());

       if(pautaCadastrada.isPresent()) {
           throw new CooperativaException("A pauta já foi criada anteriormente.");
       }
    }
}
