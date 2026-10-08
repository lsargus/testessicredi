package br.com.lsargus.testesicredi.common.mapper;

import br.com.lsargus.testesicredi.domain.model.UserAccount;
import br.com.lsargus.testesicredi.infrastruct.entity.UserAccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserAccountPersistenceMapper {
    UserAccountEntity toEntity(UserAccount domain);

    UserAccount toDomain(UserAccountEntity entity);
}
