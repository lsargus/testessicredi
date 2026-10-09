package br.com.lsargus.testesicredi.cucumber;

import br.com.lsargus.testesicredi.domain.enuns.Profile;
import br.com.lsargus.testesicredi.infrastruct.entity.AgendaEntity;
import br.com.lsargus.testesicredi.infrastruct.entity.UserAccountEntity;
import br.com.lsargus.testesicredi.infrastruct.repository.AgendaVoteRepository;
import br.com.lsargus.testesicredi.infrastruct.repository.UserAccountRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.client.EntityExchangeResult;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RequiredArgsConstructor
public class VoteSteps {

    private final ApiClient apiClient;
    private final AgendaVoteRepository agendaVoteRepository;
    private final UserAccountRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final SharedContext context;

    private AgendaEntity agenda;
    private final List<TestUser> users = new ArrayList<>();

    @When("registro 3 votos {string}, {string}, {string}")
    public void registroTresVotos(
            String vote1,
            String vote2,
            String vote3
    ) {

        voteAsUser("associado1@teste.com", "12345678", vote1);
        voteAsUser("associado2@teste.com", "12345678", vote2);
        voteAsUser("associado3@teste.com", "12345678", vote3);
    }

    private void voteAsUser(
            String email,
            String password,
            String vote
    ) {

        context.setToken(apiClient.authenticate(email, password));

        boolean approved = vote.equalsIgnoreCase("SIM");

        EntityExchangeResult<?> result =
                apiClient.vote(context.getAgendaId(), approved);

        assertEquals(
                201,
                result.getStatus().value()
        );
    }

    @Then("os 3 votos devem estar persistidos")
    public void osTresVotosDevemEstarPersistidos() {

        long count = agendaVoteRepository
                .countByAgendaId(context.getAgendaId());

        assertEquals(3, count);
    }

    @Given("existem usuários associados para votação")
    public void existemUsuariosAssociadosParaVotacao() {

        users.clear();

        users.add(createUser("associado1@teste.com", "01002003004"));

        users.add(createUser("associado2@teste.com", "02003004005"));

        users.add(createUser("associado3@teste.com", "05006007008"));
    }

    private TestUser createUser(String email, String cpf) {

        UserAccountEntity user = new UserAccountEntity();

        user.setEmail(email);
        user.setName(email);
        user.setCpf(cpf);
        user.setPasswordHash(passwordEncoder.encode("12345678"));
        user.setProfile(Profile.USER);
        user.setCreatedAt(Instant.now());
        user.setUpdatedAt(Instant.now());

        userRepository.saveAndFlush(user);

        return new TestUser(
                email,
                "12345678",
                user.getId()
        );
    }

    @When("registrei um voto")
    public void registrei_um_voto() {
        EntityExchangeResult<?> result =
                apiClient.vote(context.getAgendaId(), true);

        context.setResponse(result);
    }

    public record TestUser(
            String email,
            String password,
            UUID id
    ) {}
}
