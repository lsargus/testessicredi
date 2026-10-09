package br.com.lsargus.testesicredi.domain.model;

import br.com.lsargus.testesicredi.domain.enuns.AgendaResult;
import br.com.lsargus.testesicredi.domain.enuns.AgendaStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class Agenda {

    public static final int DEFAULT_DURATION_SECONDS = 60;
    private Integer id;
    private String name;
    private String description;
    private AgendaStatus status;
    private Instant openingDate;
    private Integer votingDurationSeconds;
    private Integer votesAgainst;
    private Integer votesInFavor;
    private AgendaResult result;
    private Instant createdAt;
    private Instant updatedAt;

    public void update( Agenda agenda, boolean cancel ) {
        if (status != AgendaStatus.CREATED) {
            throw new IllegalStateException(
                    "Agenda não pode ser alterada após abertura"
            );
        }

        if (cancel) {
            this.status = AgendaStatus.CANCELLED;
        }

        this.name = agenda.getName();
        this.description = agenda.getDescription();

        if (agenda.getVotingDurationSeconds() != null) {
            this.votingDurationSeconds = agenda.getVotingDurationSeconds();
        }
    }


    public void openVoting() {

        if (status != AgendaStatus.CREATED) {
            throw new IllegalStateException(
                    "Agenda não pode ser aberta"
            );
        }

        this.openingDate = Instant.now();
        this.status = AgendaStatus.OPEN;
    }


    public boolean hasVotingFinished() {

        if (openingDate == null) {
            return false;
        }

        return Instant.now()
                .isAfter(
                        openingDate.plusSeconds(votingDurationSeconds)
                );
    }


    public void calculateResult() {

        if (votesInFavor > votesAgainst) {
            result = AgendaResult.APPROVED;
        } else if (votesAgainst > votesInFavor) {
            result = AgendaResult.REJECTED;
        } else {
            result = AgendaResult.INDEFINITE;
        }

        status = AgendaStatus.COMPLETED;
        updatedAt = Instant.now();
    }

    public void setVotingDurationSeconds(Integer votingDurationSeconds) {
        this.votingDurationSeconds =
                votingDurationSeconds != null ? votingDurationSeconds : DEFAULT_DURATION_SECONDS;
    }
}
