package com.cwi.cooperativa.mapstruct;

import com.cwi.cooperativa.dtos.AssociadoDTO;
import com.cwi.cooperativa.entities.Associado;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AssociadoMapper {
    AssociadoDTO toDTO(Associado associado);
    Associado toEntity(AssociadoDTO associadoDTO);
    List<AssociadoDTO> toDTOList(List<Associado> associados);
}
