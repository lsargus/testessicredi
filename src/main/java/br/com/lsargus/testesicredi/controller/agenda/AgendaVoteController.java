package br.com.lsargus.testesicredi.controller.agenda;

import br.com.lsargus.testesicredi.controller.VotesApi;
import br.com.lsargus.testesicredi.domain.service.VoteService;
import br.com.lsargus.testesicredi.dto.VoteRequest;
import br.com.lsargus.testesicredi.security.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AgendaVoteController implements VotesApi {

    private final VoteService voteService;

    @Override
    public ResponseEntity<Void> computeVote(Integer id, VoteRequest voteRequest) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        try {
            AuthenticatedUser user =
                    (AuthenticatedUser) authentication.getPrincipal();

            UUID userId = user.getId();
            voteService.computeVote(id, userId, voteRequest.getApproved());
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("Usuário não autenticado", e);
        }

        return ResponseEntity.accepted().build();
    }
}
