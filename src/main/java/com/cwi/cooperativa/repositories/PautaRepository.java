package com.cwi.cooperativa.repositories;

import com.cwi.cooperativa.entities.Pauta;
import com.cwi.cooperativa.enums.StatusPauta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long> {

    Optional<Pauta> findByDescricao(@Param("descricao") String descricao);

    @Modifying
    @Transactional
    @Query("UPDATE Pauta p SET p.statusPauta = 'FECHADA' WHERE p.dataFim < :agora AND p.statusPauta = 'ABERTA'")
    void fecharPautasExpiradas(@Param("agora") LocalDateTime agora);
}
