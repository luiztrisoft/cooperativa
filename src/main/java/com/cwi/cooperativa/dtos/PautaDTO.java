package com.cwi.cooperativa.dtos;

import com.cwi.cooperativa.entities.Voto;
import com.cwi.cooperativa.enums.StatusPauta;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

public class PautaDTO {
    private Long id;
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
