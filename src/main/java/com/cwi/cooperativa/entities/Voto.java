package com.cwi.cooperativa.entities;

import com.cwi.cooperativa.enums.TipoVoto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "voto")
public class Voto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "pauta_id", nullable = false)
    @NotNull(message = "Esta votação não está associada a nenhuma pauta")
    private Pauta pauta;

    @ManyToOne
    @JoinColumn(name = "associado_id", nullable = false)
    @NotNull(message = "O associado é obrigatório")
    private Associado associado;

    @Column(name = "tipo_voto", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "É preciso escolher uma opção entre 'Sim' e 'Não' para votar")
    private TipoVoto tipoVoto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pauta getPauta() {
        return pauta;
    }

    public void setPauta(Pauta pauta) {
        this.pauta = pauta;
    }

    public Associado getAssociado() {
        return associado;
    }

    public void setAssociado(Associado associado) {
        this.associado = associado;
    }

    public TipoVoto getTipoVoto() {
        return tipoVoto;
    }

    public void setTipoVoto(TipoVoto tipoVoto) {
        this.tipoVoto = tipoVoto;
    }
}
