package br.com.lsargus.testesicredi.infrastruct.repository;

import br.com.lsargus.testesicredi.infrastruct.entity.AgendaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendaRepository extends JpaRepository<AgendaEntity, Integer> {
}
