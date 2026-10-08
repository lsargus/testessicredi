package br.com.lsargus.testesicredi.common.mapper;

import br.com.lsargus.testesicredi.domain.model.Agenda;
import br.com.lsargus.testesicredi.infrastruct.entity.AgendaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface AgendaPersistenceMapper {

    @Mapping(target = "votes", ignore = true)
    AgendaEntity toEntity(Agenda domain);

    @Mapping(target = "votes", ignore = true)
    Agenda toDomain(AgendaEntity entity);
}
