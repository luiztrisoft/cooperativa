package com.cwi.cooperativa.dtos;

import com.cwi.cooperativa.enums.StatusPauta;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public class PautaDTO {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty("id")
    private Long id;
    @JsonProperty("descricao")
    private String descricao;
    private int tempoVotacao;
    private StatusPauta statusPauta;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getTempoVotacao() {
        return tempoVotacao;
    }

    public void setTempoVotacao(int tempoVotacao) {
        this.tempoVotacao = tempoVotacao;
    }

    public StatusPauta getStatusPauta() {
        return statusPauta;
    }

    public void setStatusPauta(StatusPauta statusPauta) {
        this.statusPauta = statusPauta;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }
}
