package com.cwi.cooperativa.entities;

import com.cwi.cooperativa.enums.StatusPauta;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

//@Data
@Entity
@Table(name = "pauta")
public class Pauta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "A descrição da pauta é obrigatória")
    @Size(min = 3, max = 500, message = "A descrição da pauta deve ter entre 5 e 500 caracteres")
    private String descricao;

    @Column(name = "tempo_votacao ")
    private int tempoVotacao = 60; //Em segundos

    @Column(name = "status_pauta")
    @Enumerated(EnumType.STRING)
    private StatusPauta statusPauta = StatusPauta.FECHADA;

    @OneToMany(mappedBy = "pauta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Voto> votos;

    @Column(name = "data_inicio")
    private LocalDateTime dataInicio;

    @Column(name = "data_fim")
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

    public List<Voto> getVotos() {
        return votos;
    }

    public void setVotos(List<Voto> votos) {
        this.votos = votos;
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
