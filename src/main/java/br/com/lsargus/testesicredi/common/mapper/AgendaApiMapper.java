package br.com.lsargus.testesicredi.common.mapper;

import br.com.lsargus.testesicredi.domain.model.Agenda;
import br.com.lsargus.testesicredi.dto.AgendaCreateRequest;
import br.com.lsargus.testesicredi.dto.AgendaResponse;
import br.com.lsargus.testesicredi.dto.AgendaUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.openapitools.jackson.nullable.JsonNullable;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface AgendaApiMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "openingDate", ignore = true)
    @Mapping(target = "votesAgainst", ignore = true)
    @Mapping(target = "votesInFavor", ignore = true)
    @Mapping(target = "result", ignore = true)
    @Mapping(target = "votes", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Agenda fromCreate(AgendaCreateRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "openingDate", ignore = true)
    @Mapping(target = "votesAgainst", ignore = true)
    @Mapping(target = "votesInFavor", ignore = true)
    @Mapping(target = "result", ignore = true)
    @Mapping(target = "votes", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Agenda update(AgendaUpdateRequest request);

    AgendaResponse toResponse(Agenda agenda);

    default Instant toInstant(OffsetDateTime value) {
        return value == null ? null : value.toInstant();
    }

    default OffsetDateTime toOffsetDateTime(Instant value) {
        return value == null ? null : value.atOffset(ZoneOffset.UTC);
    }

    default JsonNullable<OffsetDateTime> toJsonNullable(Instant value) {
        return value == null
                ? JsonNullable.undefined()
                : JsonNullable.of(value.atOffset(ZoneOffset.UTC));
    }

    default JsonNullable<Integer> toJsonNullable(Integer value) {
        return value == null
                ? JsonNullable.undefined()
                : JsonNullable.of(value);
    }
}
