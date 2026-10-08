package br.com.lsargus.testesicredi.cucumber;


import br.com.lsargus.testesicredi.CucumberSpringConfiguration;
import br.com.lsargus.testesicredi.dto.AgendaResponse;
import br.com.lsargus.testesicredi.infrastruct.entity.AgendaEntity;
import br.com.lsargus.testesicredi.infrastruct.repository.AgendaRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RequiredArgsConstructor
public class AgendaSteps extends CucumberSpringConfiguration  {

    private final ApiClient apiClient;
    private final AgendaRepository agendaRepository;

    private final SharedContext context;
    private AgendaResponse agendaResponse;

    @Given("estou autenticado")
    public void estouAutenticado() {
        var loginResult = apiClient.login(
                "admin@teste.com",
                "123456"
        );

        if (loginResult.getStatus().is2xxSuccessful()) {
            var token = loginResult.getResponseBody().getAccessToken();
            context.setToken(token);
            apiClient.setToken(token);
        }
    }

    @When("envio uma requisição para criar uma pauta")
    public void envioUmaRequisicaoParaCriarUmaPauta() {

        var response = apiClient.createAgenda(
                "Pauta de teste",
                "Descrição da pauta",
                60
        );
        agendaResponse = response.getResponseBody();
        context.setAgendaId(response.getResponseBody().getId());
    }

    @Then("o status da resposta deve ser {int}")
    public void oStatusDaRespostaDeveSer(int status) {
        assertEquals(
                status,
                context.getResponse().getStatus().value()
        );
    }

    @Then("a pauta deve existir no banco")
    public void aPautaDeveExistirNoBanco() {

        assertNotNull(context.getResponse());
        assertNotNull(context.getAgendaId());

        AgendaEntity agenda = agendaRepository
                .findById(context.getAgendaId())
                .orElse(null);

        assertNotNull(agenda);
        assertEquals(
                agendaResponse.getName(),
                agenda.getName()
        );
    }

}
