package br.com.lsargus.testesicredi.cucumber;

import io.cucumber.spring.ScenarioScope;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.client.EntityExchangeResult;

import java.util.UUID;

@Component
@ScenarioScope
@Getter
@Setter
public class SharedContext {

    private String token;

    private Integer agendaId;

    private UUID sessionId;

    private EntityExchangeResult<?> response;

}