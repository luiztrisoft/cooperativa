package com.cwi.cooperativa.services;

import com.cwi.cooperativa.dtos.VotoDTO;

public interface VotoService {
    void votar(Long idPauta, VotoDTO votoDTO);
}
