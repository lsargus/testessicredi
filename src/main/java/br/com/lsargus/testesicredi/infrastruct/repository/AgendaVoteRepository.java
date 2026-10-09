package br.com.lsargus.testesicredi.infrastruct.repository;

import br.com.lsargus.testesicredi.infrastruct.entity.AgendaVoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AgendaVoteRepository extends JpaRepository<AgendaVoteEntity, UUID>  {

    boolean existsAgendaVoteEntityByAgenda_IdAndUser_Id(Integer agendaId, UUID userId);

    long countByAgendaId(Integer agendaId);

    int countByAgendaIdAndApproved(Integer agendaId, Boolean approved);
}
