package br.com.lsargus.testesicredi.domain.model;

import br.com.lsargus.testesicredi.domain.enuns.AgendaResult;
import br.com.lsargus.testesicredi.domain.enuns.AgendaStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    private final List<AgendaVote> votes = new ArrayList<>();

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

        if (votingDurationSeconds != null) {
            this.votingDurationSeconds = agenda.getVotingDurationSeconds();
        }

        this.updatedAt = Instant.now();
    }


    public void openVoting() {

        if (status != AgendaStatus.CREATED) {
            throw new IllegalStateException(
                    "Agenda não pode ser aberta"
            );
        }

        this.openingDate = Instant.now();
        this.status = AgendaStatus.OPEN;
        this.updatedAt = Instant.now();
    }


    public void addVote(AgendaVote vote) {

        if (status != AgendaStatus.OPEN) {
            throw new IllegalStateException(
                    "A votação não está aberta"
            );
        }

        if (hasUserVoted(vote.getUser())) {
            throw new IllegalStateException(
                    "Usuário já realizou voto"
            );
        }

        votes.add(vote);
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

        if (!hasVotingFinished()) {
            throw new IllegalStateException(
                    "A votação ainda não terminou"
            );
        }

        votesInFavor = (int) votes.stream()
                .filter(AgendaVote::isApproved)
                .count();

        votesAgainst = (int) votes.stream()
                .filter(v -> !v.isApproved())
                .count();


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


    private boolean hasUserVoted(UserAccount user) {
        return votes.stream()
                .anyMatch(v -> v.getUser().equals(user));
    }

    public void setVotingDurationSeconds(Integer votingDurationSeconds) {
        this.votingDurationSeconds =
                votingDurationSeconds != null ? votingDurationSeconds : DEFAULT_DURATION_SECONDS;
    }
}
