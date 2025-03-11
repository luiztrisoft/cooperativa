package com.cwi.cooperativa.mapstruct;

import com.cwi.cooperativa.dtos.PautaDTO;
import com.cwi.cooperativa.entities.Pauta;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PautaMapper {
    PautaDTO toDTO(Pauta pauta);
    Pauta toEntity(PautaDTO pautaDTO);
    List<PautaDTO>toDTOlist(List<Pauta>pautas);
}
