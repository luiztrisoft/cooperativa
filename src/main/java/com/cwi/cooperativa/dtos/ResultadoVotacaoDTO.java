package com.cwi.cooperativa.dtos;

public class ResultadoVotacaoDTO {
    private String pautaVotacao;
    private long totalVotos;
    private long votosSim;
    private long votosNao;

    public String getPautaVotacao() {
        return pautaVotacao;
    }

    public void setPautaVotacao(String pautaVotacao) {
        this.pautaVotacao = pautaVotacao;
    }

    public long getTotalVotos() {
        return totalVotos;
    }

    public void setTotalVotos(long totalVotos) {
        this.totalVotos = totalVotos;
    }

    public long getVotosSim() {
        return votosSim;
    }

    public void setVotosSim(long votosSim) {
        this.votosSim = votosSim;
    }

    public long getVotosNao() {
        return votosNao;
    }

    public void setVotosNao(long votosNao) {
        this.votosNao = votosNao;
    }
}
