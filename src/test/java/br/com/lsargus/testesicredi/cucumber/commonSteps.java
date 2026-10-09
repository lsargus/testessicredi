package br.com.lsargus.testesicredi.cucumber;

import br.com.lsargus.testesicredi.CucumberSpringConfiguration;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.RequiredArgsConstructor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor
public class commonSteps extends CucumberSpringConfiguration {

    private final ApiClient apiClient;

    private final SharedContext context;

    @Given("estou autenticado como administrador")
    public void estouAutenticado() {
        var loginResult = apiClient.login(
                "admin@teste.com",
                "admin123"
        );

        if (loginResult.getStatus().is2xxSuccessful()) {
            var token = loginResult.getResponseBody().getAccessToken();
            context.setToken(token);
            context.setResponse(loginResult);
            apiClient.setToken(token);
        }
    }

    @Then("o status da resposta deve ser {int}")
    public void oStatusDaRespostaDeveSer(int status) {
        assertEquals(
                status,
                context.getResponse().getStatus().value()
        );
    }

    @Then("a mensagem deve ser {string}")
    public void a_mensagem_deve_ser(String msg) {
        assert context.getResponse().getResponseBody() != null;
        assertTrue(
                context.getResponse().getResponseBody().toString().contains(msg));
    }
}
