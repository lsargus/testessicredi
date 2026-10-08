package br.com.lsargus.testesicredi.security;

import br.com.lsargus.testesicredi.infrastruct.entity.UserAccountEntity;
import br.com.lsargus.testesicredi.infrastruct.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final AuthRepository repository;

    @Override
    public AuthenticatedUser loadUserByUsername(@NonNull String uuid)
            throws UsernameNotFoundException {

        UserAccountEntity person = repository
                .findById(UUID.fromString(uuid))
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Usuário não encontrado"
                        )
                );


        return new AuthenticatedUser(
                person.getId(),
                person.getEmail(),
                person.getPasswordHash(),
                List.of(new SimpleGrantedAuthority("ROLE_" + person.getProfile()))
        );
    }
}
