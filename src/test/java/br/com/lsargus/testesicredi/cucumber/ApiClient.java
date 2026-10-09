package br.com.lsargus.testesicredi.cucumber;

import br.com.lsargus.testesicredi.dto.AgendaCreateRequest;
import br.com.lsargus.testesicredi.dto.AgendaResponse;
import br.com.lsargus.testesicredi.dto.LoginRequest;
import br.com.lsargus.testesicredi.dto.TokenResponse;
import br.com.lsargus.testesicredi.dto.VoteRequest;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.client.EntityExchangeResult;
import org.springframework.test.web.servlet.client.RestTestClient;

@Component
public class ApiClient {

    private final RestTestClient client;

    @Setter
    private String token;

    public ApiClient(RestTestClient client) {

        this.client = client;
    }

    public EntityExchangeResult<TokenResponse> login( String email, String password ) {

        LoginRequest request = new LoginRequest()
                .email(email)
                .password(password);

        return client.post()
                .uri("/auth/login")
                .body(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TokenResponse.class)
                .returnResult();
    }

    public String authenticate( String email, String password ) {

        TokenResponse response = login(email, password)
                .getResponseBody();

        token = response.getAccessToken();
        return token;
    }

    public EntityExchangeResult<AgendaResponse> createAgenda(
            String name,
            String description,
            Integer votingDurationSeconds
    ) {
        AgendaCreateRequest request = new AgendaCreateRequest()
                .name(name)
                .description(description)
                .votingDurationSeconds(votingDurationSeconds);

        return client.post()
                .uri("/agendas")
                .header("Authorization", "Bearer " + token)
                .body(request)
                .exchange()
                .expectBody(AgendaResponse.class)
                .returnResult();
    }

    public EntityExchangeResult<AgendaResponse> openAgenda( Integer agendaId ) {
        return client.post()
                .uri("/agendas/{id}/opening", agendaId)
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectBody(AgendaResponse.class)
                .returnResult();
    }

    public EntityExchangeResult<AgendaResponse> closeAgenda( Integer agendaId ) {
        return client.post()
                .uri("/agendas/{id}/settlement", agendaId)
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectBody(AgendaResponse.class)
                .returnResult();
    }

    public EntityExchangeResult<VoteRequest> vote (Integer agendId, boolean vote) {
        VoteRequest request = new VoteRequest();
        request.setApproved(vote);

        return client.post()
                .uri("agendas/{id}/votes", agendId)
                .header("Authorization", "Bearer " + token)
                .body(request)
                .exchange()
                .expectBody(VoteRequest.class)
                .returnResult();

    }

}
