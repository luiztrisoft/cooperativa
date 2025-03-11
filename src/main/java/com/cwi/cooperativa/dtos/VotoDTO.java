package com.cwi.cooperativa.dtos;

import com.cwi.cooperativa.enums.TipoVoto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class VotoDTO {
    @NotNull(message = "É necessário selecionar uma das opções para votar.")
    private TipoVoto tipoVoto;

    @NotBlank(message = "O CPF do associado é obrigatório.")
    private String cpfAssociado;

    public TipoVoto getTipoVoto() {
        return tipoVoto;
    }

    public void setTipoVoto(TipoVoto tipoVoto) {
        this.tipoVoto = tipoVoto;
    }

    public String getCpfAssociado() {
        return cpfAssociado;
    }

    public void setCpfAssociado(String cpfAssociado) {
        this.cpfAssociado = cpfAssociado;
    }
}
