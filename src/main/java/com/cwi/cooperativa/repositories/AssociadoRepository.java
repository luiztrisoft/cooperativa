package com.cwi.cooperativa.repositories;

import com.cwi.cooperativa.entities.Associado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssociadoRepository extends JpaRepository<Associado, Long> {

    Optional<Associado> findByCpf(@Param("cpf")String cpf);
}
