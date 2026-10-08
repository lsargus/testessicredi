package br.com.lsargus.testesicredi.common.mapper;

import br.com.lsargus.testesicredi.domain.model.AgendaVote;
import br.com.lsargus.testesicredi.infrastruct.entity.AgendaVoteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.ERROR,
    uses = {
        UserAccountPersistenceMapper.class,
        AgendaPersistenceMapper.class
    })
public interface AgendaVotePersistenceMapper {

    AgendaVoteEntity toEntity(AgendaVote domain);
}
