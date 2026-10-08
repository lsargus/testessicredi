package br.com.lsargus.testesicredi.common.mapper;

import br.com.lsargus.testesicredi.domain.model.UserAccount;
import br.com.lsargus.testesicredi.dto.CreatePersonRequest;
import br.com.lsargus.testesicredi.dto.PersonResponse;
import br.com.lsargus.testesicredi.dto.UpdatePersonRequest;
import org.mapstruct.*;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserAccountApiMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "profile", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    UserAccount fromCreate(CreatePersonRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    UserAccount update(UpdatePersonRequest request);

    PersonResponse toResponse(UserAccount person);

    default Instant toInstant(OffsetDateTime value) {
        return value == null ? null : value.toInstant();
    }

    default OffsetDateTime toOffsetDateTime(Instant value) {
        return value == null ? null : value.atOffset(ZoneOffset.UTC);
    }
}
