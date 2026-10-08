package br.com.lsargus.testesicredi.domain.service;

import br.com.lsargus.testesicredi.dto.LoginRequest;
import br.com.lsargus.testesicredi.dto.TokenResponse;
import br.com.lsargus.testesicredi.infrastruct.repository.AuthRepository;
import br.com.lsargus.testesicredi.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public TokenResponse login(LoginRequest request) {

        var person = repository.findByEmail(
                UserAccountService.normalizeEmail(request.getEmail())
        ).orElseThrow(() ->
                new BadCredentialsException("Credenciais inválidas")
        );

        if (!passwordEncoder.matches(
                request.getPassword(),
                person.getPasswordHash()
        )) {
            throw new BadCredentialsException("Credenciais inválidas");
        }

        String token = jwtService.generateToken(person);

        return new TokenResponse()
                .accessToken(token)
                .tokenType("Bearer")
                .expiresIn(jwtService.getExpirationSeconds());
    }
}