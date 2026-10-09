package br.com.lsargus.testesicredi.controller.agenda;

import br.com.lsargus.testesicredi.controller.VotesApi;
import br.com.lsargus.testesicredi.domain.service.VoteService;
import br.com.lsargus.testesicredi.dto.VoteRequest;
import br.com.lsargus.testesicredi.security.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AgendaVoteController implements VotesApi {

    private final VoteService voteService;

    @Override
    public ResponseEntity<Void> computeVote(Integer id, VoteRequest voteRequest) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || !(authentication.getPrincipal() instanceof AuthenticatedUser user)) {
            throw new AuthenticationCredentialsNotFoundException(
                    "Usuário não autenticado"
            );
        }

        voteService.computeVote(id, user.getId(), voteRequest.getApproved());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
