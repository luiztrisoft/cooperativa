package com.cwi.cooperativa.service;

import com.cwi.cooperativa.dtos.PautaDTO;
import com.cwi.cooperativa.dtos.ResultadoVotacaoDTO;
import com.cwi.cooperativa.entities.Pauta;
import com.cwi.cooperativa.entities.Voto;
import com.cwi.cooperativa.enums.StatusPauta;
import com.cwi.cooperativa.enums.TipoVoto;
import com.cwi.cooperativa.mapstruct.PautaMapper;
import com.cwi.cooperativa.repositories.PautaRepository;
import com.cwi.cooperativa.exceptions.CooperativaException;
import com.cwi.cooperativa.services.impl.PautaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PautaServiceTest {

    @Mock
    private PautaRepository pautaRepository;

    @Mock
    private PautaMapper pautaMapper;

    @InjectMocks
    private PautaServiceImpl pautaService;

    private Pauta pauta;
    private PautaDTO pautaDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        pauta = new Pauta();
        pauta.setId(1L);
        pauta.setDescricao("Pauta para votação");
        pauta.setTempoVotacao(60);
        pauta.setStatusPauta(StatusPauta.FECHADA);

        pautaDTO = new PautaDTO();
        pautaDTO.setId(1L);
        pautaDTO.setDescricao("Pauta para votação");
        pautaDTO.setStatusPauta(StatusPauta.FECHADA);
    }

    @Test
    void deveCriarPautaComSucesso() {
        when(pautaMapper.toEntity(pautaDTO)).thenReturn(pauta);
        when(pautaRepository.save(pauta)).thenReturn(pauta);
        when(pautaMapper.toDTO(pauta)).thenReturn(pautaDTO);

        PautaDTO resultado = pautaService.salvar(pautaDTO);

        assertNotNull(resultado);
        assertEquals(pautaDTO.getDescricao(), resultado.getDescricao());
    }

    @Test
    void deveAjustarTempoVotacaoPara60SeMenor() {
        pautaDTO.setTempoVotacao(45);
        pauta.setTempoVotacao(45);
        when(pautaMapper.toEntity(pautaDTO)).thenReturn(pauta);

        when(pautaRepository.save(pauta)).thenReturn(pauta);
        when(pautaMapper.toDTO(any(Pauta.class))).thenAnswer(invocation -> {
            Pauta pautaSalva = invocation.getArgument(0);
            PautaDTO dto = new PautaDTO();
            dto.setId(pautaSalva.getId());
            dto.setDescricao(pautaSalva.getDescricao());
            dto.setTempoVotacao(pautaSalva.getTempoVotacao());
            dto.setStatusPauta(pautaSalva.getStatusPauta());
            return dto;
        });
        PautaDTO resultado = pautaService.salvar(pautaDTO);
        assertEquals(60, resultado.getTempoVotacao(), "O tempo de votação deve ser ajustado para 60");
    }

    @Test
    void naoDeveAjustarTempoVotacaoSeMaiorQue60() {
        pautaDTO.setTempoVotacao(120);
        pauta.setTempoVotacao(120);

        when(pautaMapper.toEntity(pautaDTO)).thenReturn(pauta);
        when(pautaRepository.save(pauta)).thenReturn(pauta);
        when(pautaMapper.toDTO(pauta)).thenReturn(pautaDTO);

        PautaDTO resultado = pautaService.salvar(pautaDTO);
        assertEquals(120, resultado.getTempoVotacao());
    }

    @Test
    void deveLancarExcecaoQuandoPautaNaoEncontrada() {
        when(pautaMapper.toEntity(pautaDTO)).thenThrow(new CooperativaException("Pauta não encontrada"));
        Exception exception = assertThrows(CooperativaException.class, () -> pautaService.salvar(pautaDTO));
        assertEquals("Pauta não encontrada", exception.getMessage());
    }

    @Test
    void listaDeveRetornarPautaDTO() {
        List<Pauta> pautas = List.of(pauta);
        List<PautaDTO> pautasDTO = List.of(pautaDTO);

        when(pautaRepository.findAll()).thenReturn(pautas);
        when(pautaMapper.toDTOlist(pautas)).thenReturn(pautasDTO);

        List<PautaDTO> resultado = pautaService.listar();

        assertEquals(1, resultado.size());
        assertEquals(pautaDTO, resultado.get(0));
    }

    @Test
    void buscarDeveRetornarPautaDTOQuandoPautaExiste() {
        when(pautaRepository.findById(1L)).thenReturn(Optional.of(pauta));
        when(pautaMapper.toDTO(pauta)).thenReturn(pautaDTO);

        PautaDTO resultado = pautaService.buscar(1L);

        assertEquals(pautaDTO.getId(), resultado.getId());
        assertEquals(pautaDTO.getDescricao(), resultado.getDescricao());
    }

    @Test
    void buscarDeveLancarExcecaoQuandoPautaNaoExiste() {
        when(pautaRepository.findById(1L)).thenReturn(Optional.empty());
        CooperativaException exception = assertThrows(CooperativaException.class, () -> pautaService.buscar(1L));
        assertEquals("Pauta não encontrada.", exception.getMessage());
    }

    @Test
    void deveAbrirVotacaoComSucesso() {
        when(pautaRepository.findById(1L)).thenReturn(Optional.of(pauta));
        when(pautaMapper.toDTO(any(Pauta.class))).thenAnswer(invocation -> {
            Pauta pautaSalva = invocation.getArgument(0);
            PautaDTO dto = new PautaDTO();
            dto.setId(pautaSalva.getId());
            dto.setDescricao(pautaSalva.getDescricao());
            dto.setStatusPauta(pautaSalva.getStatusPauta());
            dto.setDataInicio(pautaSalva.getDataInicio());
            dto.setDataFim(pautaSalva.getDataFim());
            return dto;
        });

        when(pautaRepository.save(pauta)).thenReturn(pauta);
        PautaDTO resultado = pautaService.abrirVotacao(1L);

        assertEquals(StatusPauta.ABERTA, resultado.getStatusPauta(), "O status da pauta deve ser ABERTA");
        assertNotNull(resultado.getDataInicio(), "A data de início não pode ser nula");
        assertNotNull(resultado.getDataFim(), "A data de fim não pode ser nula");
        verify(pautaRepository, times(1)).findById(1L);
        verify(pautaRepository, times(1)).save(pauta);
    }

    @Test
    void abrirVotacaoDeveLancarExcecaoQuandoPautaNaoExiste() {
        when(pautaRepository.findById(1L)).thenReturn(Optional.empty());
        CooperativaException exception = assertThrows(CooperativaException.class, () -> pautaService.abrirVotacao(1L));
        assertEquals("Pauta não encontrada.", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoPautaJaExiste() {
        PautaDTO pautaDTO = new PautaDTO();
        pautaDTO.setDescricao("Pauta existente");
        Pauta pautaExistente = new Pauta();
        pautaExistente.setDescricao("Pauta existente");

        when(pautaRepository.findByDescricao("Pauta existente")).thenReturn(Optional.of(pautaExistente));
        when(pautaMapper.toEntity(pautaDTO)).thenReturn(pautaExistente);

        CooperativaException exception = assertThrows(CooperativaException.class, () -> pautaService.salvar(pautaDTO));
        assertEquals("A pauta já foi criada anteriormente.", exception.getMessage());
        verify(pautaRepository, times(1)).findByDescricao("Pauta existente");
    }

    @Test
    void listaDeveRetornarVaziaQuandoNaoHouverPautas() {
        when(pautaRepository.findAll()).thenReturn(List.of());
        when(pautaMapper.toDTOlist(List.of())).thenReturn(List.of());
        List<PautaDTO> resultado = pautaService.listar();
        assertTrue(resultado.isEmpty());
    }

    @Test
    void deveRetornarTrueQuandoPautaEstaAberta() {
        Pauta pauta = new Pauta();
        pauta.setStatusPauta(StatusPauta.ABERTA);
        pauta.setDataInicio(LocalDateTime.now().minusMinutes(10));
        pauta.setDataFim(LocalDateTime.now().plusMinutes(50));

        assertTrue(pautaService.isPautaAberta(pauta));
    }

    @Test
    void deveLancarExcecaoQuandoPautaEstaFechada() {
        Pauta pauta = new Pauta();
        pauta.setStatusPauta(StatusPauta.FECHADA);
        pauta.setDataInicio(LocalDateTime.now().minusMinutes(10));
        pauta.setDataFim(LocalDateTime.now().plusMinutes(50));

        CooperativaException exception = assertThrows(CooperativaException.class, () -> pautaService.isPautaAberta(pauta));
        assertEquals("Esta pauta está fora do período ou já foi encerrada.", exception.getMessage());
    }

    @Test
    void abrirVotacaoDeveLancarExcecaoQuandoPautaJaAberta() {
        pauta.setStatusPauta(StatusPauta.ABERTA);
        when(pautaRepository.findById(1L)).thenReturn(Optional.of(pauta));
        CooperativaException exception = assertThrows(CooperativaException.class, () -> pautaService.abrirVotacao(1L));
        assertEquals("A pauta já está aberta.", exception.getMessage());
        verify(pautaRepository, times(1)).findById(1L);
    }

    @Test
    void deveLancarExcecaoQuandoDatasNaoEstaoDefinidas() {
        Pauta pauta = new Pauta();
        pauta.setStatusPauta(StatusPauta.ABERTA);

        CooperativaException exception = assertThrows(CooperativaException.class, () -> pautaService.isPautaAberta(pauta));
        assertEquals("É necessário parametrizar o período da votação.", exception.getMessage());
    }

    @Test
    void deveContabilizarVotosCorretamente() {
        Pauta pauta = new Pauta();
        pauta.setDescricao("Pauta de teste");
        Voto votoSim = new Voto();
        votoSim.setTipoVoto(TipoVoto.SIM);
        Voto votoNao = new Voto();
        votoNao.setTipoVoto(TipoVoto.NAO);
        pauta.setVotos(List.of(votoSim, votoNao, votoSim));

        when(pautaRepository.findById(1L)).thenReturn(Optional.of(pauta));
        ResultadoVotacaoDTO resultado = pautaService.contabilizarVotos(1L);

        assertEquals("Pauta de teste", resultado.getPautaVotacao());
        assertEquals(3, resultado.getTotalVotos());
        assertEquals(2, resultado.getVotosSim());
        assertEquals(1, resultado.getVotosNao());
    }

    @Test
    void deveFecharPautasExpiradas() {
        pautaService.fecharPautasExpiradas();
        verify(pautaRepository, times(1)).fecharPautasExpiradas(any(LocalDateTime.class));
    }

}


