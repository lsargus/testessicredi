package br.com.lsargus.testesicredi.domain.service;

import br.com.lsargus.testesicredi.common.exception.ConflictException;
import br.com.lsargus.testesicredi.common.exception.NotFoundException;
import br.com.lsargus.testesicredi.common.mapper.UserAccountPersistenceMapper;
import br.com.lsargus.testesicredi.domain.enuns.Profile;
import br.com.lsargus.testesicredi.domain.model.UserAccount;
import br.com.lsargus.testesicredi.infrastruct.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Locale;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserAccountService {
    private final UserAccountRepository repository;
    private final UserAccountPersistenceMapper persistenceMapper;
    private final PasswordEncoder encoder;

    @Transactional
    public UserAccount create(UserAccount userAccount, String password) {
        userAccount.setProfile(Profile.USER);
        userAccount.setPasswordHash(encoder.encode(password));

        try {
            return persistenceMapper.toDomain(repository.saveAndFlush(persistenceMapper.toEntity(userAccount)));
        } catch (DataIntegrityViolationException _) {
            throw new ConflictException("CPF ou email ja cadastrado");
        }
    }

    @Transactional
    public UserAccount update(UUID id, UserAccount userAccount) {
        UserAccount persistedUser = persistenceMapper.toDomain(repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario nao encontrado")));
        String email = normalizeEmail(userAccount.getEmail());

        persistedUser.setName(userAccount.getName());
        persistedUser.setEmail(email);
        persistedUser.setProfile(userAccount.getProfile());
        persistedUser.setUpdatedAt(Instant.now());
        try {
            return persistenceMapper.toDomain(repository.saveAndFlush(persistenceMapper.toEntity(persistedUser)));
        } catch (DataIntegrityViolationException _) {
            throw new ConflictException("CPF ou email ja cadastrado");
        }
    }

    public static String normalizeEmail(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }
}
