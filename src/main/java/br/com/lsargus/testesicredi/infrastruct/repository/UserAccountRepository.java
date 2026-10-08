package br.com.lsargus.testesicredi.infrastruct.repository;

import br.com.lsargus.testesicredi.infrastruct.entity.UserAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserAccountRepository extends JpaRepository<UserAccountEntity, UUID> {
    Optional<UserAccountEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByCpf(String cpf);

    boolean existsByEmailAndIdNot(String email, UUID id);

    boolean existsByCpfAndIdNot(String cpf, UUID id);
}
