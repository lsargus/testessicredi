package br.com.lsargus.testesicredi.controller.auth;

import br.com.lsargus.testesicredi.controller.AuthApi;
import br.com.lsargus.testesicredi.dto.LoginRequest;
import br.com.lsargus.testesicredi.dto.TokenResponse;
import br.com.lsargus.testesicredi.domain.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

  private final AuthService service;

    @Override
    public ResponseEntity<TokenResponse> login(LoginRequest request) {
        return ResponseEntity.ok(service.login(request));
    }
}
