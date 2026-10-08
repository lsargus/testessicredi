package br.com.lsargus.testesicredi.controller.agenda;


import br.com.lsargus.testesicredi.common.mapper.AgendaApiMapper;
import br.com.lsargus.testesicredi.controller.AgendaApi;
import br.com.lsargus.testesicredi.domain.service.AgendaService;
import br.com.lsargus.testesicredi.dto.AgendaCreateRequest;
import br.com.lsargus.testesicredi.dto.AgendaResponse;
import br.com.lsargus.testesicredi.dto.AgendaUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AgendaController implements AgendaApi {

    private final AgendaService service;

    private final AgendaApiMapper apiMapper;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AgendaResponse> createAgenda(AgendaCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(apiMapper.toResponse(service.create(apiMapper.fromCreate(request))));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AgendaResponse> openingAgenda(Integer id) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(apiMapper.toResponse(service.openingAgenda(id)));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AgendaResponse> settlement(Integer id) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(apiMapper.toResponse(service.settlement(id)));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AgendaResponse> updateAgenda(Integer id, AgendaUpdateRequest request) {
        return ResponseEntity.ok(
                apiMapper.toResponse(service.update(id, apiMapper.update(request), request.getCancel())));
    }
}
