package br.com.lsargus.testesicredi.cucumber;

import br.com.lsargus.testesicredi.CucumberSpringConfiguration;
import br.com.lsargus.testesicredi.dto.TokenResponse;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.client.EntityExchangeResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RequiredArgsConstructor
public class AuthenticationSteps extends CucumberSpringConfiguration {

    private final ApiClient apiClient;
    private final SharedContext context;

    @Given("existe um usuário administrador")
    public void existeUmUsuarioAdministrador() {
        // O administrador já foi criado pelo Flyway.
    }

    @When("envio uma requisição de login com email {string} e senha {string}")
    public void login(String email, String password) {

        EntityExchangeResult<TokenResponse> response =
                apiClient.login(email, password);

        context.setResponse(response);

        if (response.getStatus().is2xxSuccessful()) {
            context.setToken(response.getResponseBody().getAccessToken());
        }
    }

    @Then("o status da resposta deve ser {int}")
    public void validarStatus(Integer status) {

        assertEquals(
                HttpStatus.valueOf(status),
                context.getResponse().getStatus()
        );
    }

    @Then("deve retornar um token JWT")
    public void validarToken() {

        assertNotNull(context.getToken());

        assertFalse(context.getToken().isBlank());
    }
}
