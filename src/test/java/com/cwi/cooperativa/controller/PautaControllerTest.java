package com.cwi.cooperativa.controller;

import com.cwi.cooperativa.controllers.PautaController;
import com.cwi.cooperativa.dtos.PautaDTO;
import com.cwi.cooperativa.dtos.ResultadoVotacaoDTO;
import com.cwi.cooperativa.dtos.VotoDTO;
import com.cwi.cooperativa.enums.TipoVoto;
import com.cwi.cooperativa.services.PautaService;
import com.cwi.cooperativa.services.VotoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PautaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PautaService pautaService;

    @Mock
    private VotoService votoService;

    @InjectMocks
    private PautaController pautaController;

    private PautaDTO pautaDTO;
    private VotoDTO votoDTO;
    private ResultadoVotacaoDTO resultadoVotacaoDTO;

    @BeforeEach
    void setUp() {
        // Setup MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(pautaController).build();

        // Setup DTOs para os testes
        pautaDTO = new PautaDTO();
        pautaDTO.setId(1L);
        pautaDTO.setDescricao("Pauta de Teste");

        votoDTO = new VotoDTO();
        votoDTO.setTipoVoto(TipoVoto.SIM);

        resultadoVotacaoDTO = new ResultadoVotacaoDTO();
        resultadoVotacaoDTO.setPautaVotacao("Pauta de Teste");
        resultadoVotacaoDTO.setTotalVotos(10);
        resultadoVotacaoDTO.setVotosSim(6);
        resultadoVotacaoDTO.setVotosNao(4);
    }
    @Test
    void testSalvarPauta() throws Exception {
        when(pautaService.salvar(any(PautaDTO.class))).thenReturn(pautaDTO);
        mockMvc.perform(post("/api/v1/pautas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(pautaDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.descricao").value("Pauta de Teste"));
    }

    @Test
    void testVotar() throws Exception {
        doNothing().when(votoService).votar(anyLong(), any(VotoDTO.class));

        mockMvc.perform(post("/api/v1/pautas/1/votar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(votoDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void testAbrirVotacao() throws Exception {
        when(pautaService.abrirVotacao(anyLong())).thenReturn(pautaDTO);

        mockMvc.perform(put("/api/v1/pautas/1/abrir-votacao"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.descricao").value("Pauta de Teste"));
    }

    @Test
    void testListarPautas() throws Exception {
        List<PautaDTO> pautas = Collections.singletonList(pautaDTO);
        when(pautaService.listar()).thenReturn(pautas);

        mockMvc.perform(get("/api/v1/pautas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].descricao").value("Pauta de Teste"));
    }

    @Test
    void testBuscarPauta() throws Exception {
        when(pautaService.buscar(anyLong())).thenReturn(pautaDTO);

        mockMvc.perform(get("/api/v1/pautas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.descricao").value("Pauta de Teste"));
    }

    @Test
    void testContabilizarVotos() throws Exception {
        when(pautaService.contabilizarVotos(anyLong())).thenReturn(resultadoVotacaoDTO);

        mockMvc.perform(get("/api/v1/pautas/1/contabilizar-votos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pautaVotacao").value("Pauta de Teste"))
                .andExpect(jsonPath("$.totalVotos").value(10))
                .andExpect(jsonPath("$.votosSim").value(6))
                .andExpect(jsonPath("$.votosNao").value(4));
    }
}
