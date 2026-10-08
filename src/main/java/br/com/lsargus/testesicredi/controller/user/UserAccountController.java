package br.com.lsargus.testesicredi.controller.user;

import br.com.lsargus.testesicredi.controller.PersonApi;
import br.com.lsargus.testesicredi.dto.CreatePersonRequest;
import br.com.lsargus.testesicredi.dto.PersonResponse;
import br.com.lsargus.testesicredi.dto.UpdatePersonRequest;
import br.com.lsargus.testesicredi.common.mapper.UserAccountApiMapper;
import br.com.lsargus.testesicredi.domain.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserAccountController implements PersonApi {
    private final UserAccountService service;

    private final UserAccountApiMapper apiMapper;

    @Override
    public ResponseEntity<PersonResponse> createPerson(CreatePersonRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(apiMapper.toResponse(service.create(apiMapper.fromCreate(request), request.getPassword())));
    }

    @Override
    @PreAuthorize("#id.toString() == authentication.name or hasRole('ADMIN')")
    public ResponseEntity<PersonResponse> updatePerson(UUID id, UpdatePersonRequest request) {
        return ResponseEntity.ok(apiMapper.toResponse(service.update(id, apiMapper.update(request))));
    }
}
