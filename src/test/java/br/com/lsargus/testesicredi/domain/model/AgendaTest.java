package br.com.lsargus.testesicredi.domain.model;

import br.com.lsargus.testesicredi.domain.enuns.AgendaResult;
import br.com.lsargus.testesicredi.domain.enuns.AgendaStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class AgendaTest {

    private Agenda agenda;

    @BeforeEach
    void setUp() {
        agenda = new Agenda();
        agenda.setName("Pauta original");
        agenda.setDescription("Descrição original");
        agenda.setStatus(AgendaStatus.CREATED);
        agenda.setVotingDurationSeconds(60);
    }

    @Test
    void shouldUpdateAgendaWhenStatusIsCreated() {
        Agenda update = new Agenda();
        update.setName("Pauta atualizada");
        update.setDescription("Nova descrição");
        update.setVotingDurationSeconds(120);

        agenda.update(update, false);

        assertThat(agenda.getName()).isEqualTo("Pauta atualizada");
        assertThat(agenda.getDescription()).isEqualTo("Nova descrição");
        assertThat(agenda.getVotingDurationSeconds()).isEqualTo(120);
        assertThat(agenda.getStatus()).isEqualTo(AgendaStatus.CREATED);
    }

    @Test
    void shouldCancelAgendaWhenCancelIsTrue() {
        Agenda update = new Agenda();
        update.setName("Pauta atualizada");
        update.setDescription("Nova descrição");

        agenda.update(update, true);

        assertThat(agenda.getStatus()).isEqualTo(AgendaStatus.CANCELLED);
    }

    @Test
    void shouldNotUpdateAgendaWhenStatusIsNotCreated() {
        agenda.setStatus(AgendaStatus.OPEN);

        Agenda update = new Agenda();
        update.setName("Nova pauta");

        assertThatThrownBy(() -> agenda.update(update, false))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Agenda não pode ser alterada após abertura");
    }

    @Test
    void shouldOpenVotingWhenAgendaIsCreated() {
        agenda.openVoting();

        assertThat(agenda.getStatus()).isEqualTo(AgendaStatus.OPEN);
        assertThat(agenda.getOpeningDate()).isNotNull();
    }

    @Test
    void shouldNotOpenVotingWhenAgendaIsAlreadyOpen() {
        agenda.setStatus(AgendaStatus.OPEN);

        assertThatThrownBy(() -> agenda.openVoting())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Agenda não pode ser aberta");
    }

    @Test
    void shouldNotOpenVotingWhenAgendaIsCancelled() {
        agenda.setStatus(AgendaStatus.CANCELLED);

        assertThatThrownBy(() -> agenda.openVoting())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Agenda não pode ser aberta");
    }

    @Test
    void shouldApproveAgendaWhenVotesInFavorAreGreater() {
        agenda.setVotesInFavor(5);
        agenda.setVotesAgainst(3);

        agenda.calculateResult();

        assertThat(agenda.getResult()).isEqualTo(AgendaResult.APPROVED);
        assertThat(agenda.getStatus()).isEqualTo(AgendaStatus.COMPLETED);
        assertThat(agenda.getUpdatedAt()).isNotNull();
    }

    @Test
    void shouldRejectAgendaWhenVotesAgainstAreGreater() {
        agenda.setVotesInFavor(2);
        agenda.setVotesAgainst(4);

        agenda.calculateResult();

        assertThat(agenda.getResult()).isEqualTo(AgendaResult.REJECTED);
        assertThat(agenda.getStatus()).isEqualTo(AgendaStatus.COMPLETED);
    }

    @Test
    void shouldSetIndefiniteResultWhenVotesAreTied() {
        agenda.setVotesInFavor(3);
        agenda.setVotesAgainst(3);

        agenda.calculateResult();

        assertThat(agenda.getResult()).isEqualTo(AgendaResult.INDEFINITE);
        assertThat(agenda.getStatus()).isEqualTo(AgendaStatus.COMPLETED);
    }

    @Test
    void shouldSetIndefiniteResultWhenThereAreNoVotes() {
        agenda.setVotesInFavor(0);
        agenda.setVotesAgainst(0);

        agenda.calculateResult();

        assertThat(agenda.getResult()).isEqualTo(AgendaResult.INDEFINITE);
        assertThat(agenda.getStatus()).isEqualTo(AgendaStatus.COMPLETED);
    }
}
