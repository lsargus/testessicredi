package br.com.lsargus.testesicredi.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class AgendaVote {

    private UUID id;
    private UserAccount user;
    private Agenda agenda;
    private boolean approved;
    private Instant createdAt;
}
