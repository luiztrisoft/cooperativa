package com.cwi.cooperativa.repositories;

import com.cwi.cooperativa.entities.Associado;
import com.cwi.cooperativa.entities.Pauta;
import com.cwi.cooperativa.entities.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {

    Voto findByPautaAndAssociado(Pauta pauta, Associado associado);
}
