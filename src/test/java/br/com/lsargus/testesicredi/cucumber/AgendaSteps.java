package br.com.lsargus.testesicredi.cucumber;


import br.com.lsargus.testesicredi.CucumberSpringConfiguration;
import br.com.lsargus.testesicredi.dto.AgendaResponse;
import br.com.lsargus.testesicredi.infrastruct.entity.AgendaEntity;
import br.com.lsargus.testesicredi.infrastruct.repository.AgendaRepository;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@RequiredArgsConstructor
public class AgendaSteps extends CucumberSpringConfiguration  {

    private final ApiClient apiClient;
    private final AgendaRepository agendaRepository;

    private final SharedContext context;
    private AgendaResponse agendaResponse;

    @When("envio uma requisição para criar uma pauta")
    public void envioUmaRequisicaoParaCriarUmaPauta() {

        var response = apiClient.createAgenda(
                "Pauta de teste",
                "Descrição da pauta",
                60
        );
        agendaResponse = response.getResponseBody();
        context.setAgendaId(response.getResponseBody().getId());
        context.setResponse(response);
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

    @Then("a openingDate deve ser nula")
    public void openingDateDeveSerNula() {

        AgendaEntity agenda = agendaRepository
                .findById(context.getAgendaId())
                .orElseThrow();

        assertNull(agenda.getOpeningDate());
    }

    @When("abro a pauta")
    public void abroAPauta() {

        var response = apiClient.openAgenda(context.getAgendaId());

        agendaResponse = response
                .getResponseBody();
        context.setResponse(response);
    }

    @Then("o status da pauta deve ser {string}")
    public void oStatusDaPautaDeveSer(String status) {

        assertEquals(
                status,
                agendaResponse.getStatus().getValue()
        );
    }

    @Then("a openingDate não deve ser nula")
    public void openingDateNaoDeveSerNula() {

        assertNotNull(
                agendaResponse.getOpeningDate()
        );
    }

    @When("encerro a pauta")
    public void encerroAPauta() {

        var response = apiClient.closeAgenda(context.getAgendaId());

        agendaResponse = response
                .getResponseBody();
        context.setResponse(response);
    }

    @Then("os votos devem ter sido processados")
    public void osVotosDevemTerSidoProcessados() {

        AgendaEntity agenda = agendaRepository
                .findById(context.getAgendaId())
                .orElseThrow();

        assertEquals(2, agenda.getVotesInFavor());
        assertEquals(1, agenda.getVotesAgainst());
    }

    @Then("o resultado deve ser {string}")
    public void oResultadoDeveSer(String result) {

        AgendaEntity agenda = agendaRepository
                .findById(context.getAgendaId())
                .orElseThrow();

        assertEquals(
                result,
                agenda.getResult().toString()
        );
    }

    @When("o período da pauta é encerrado")
    public void oPeriodoDaPautaEEncerrado() {
        AgendaEntity agenda = agendaRepository.findById(context.getAgendaId())
                .orElseThrow();
        agenda.setOpeningDate(
                Instant.now().minusSeconds(120)
        );

        agendaRepository.save(agenda);
    }

}
