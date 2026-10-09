package br.com.lsargus.testesicredi.infrastruct.entity;

import br.com.lsargus.testesicredi.domain.enuns.AgendaResult;
import br.com.lsargus.testesicredi.domain.enuns.AgendaStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "agenda")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgendaEntity {
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "AGENDA_SEQ")
    @SequenceGenerator(
            name = "AGENDA_SEQ",
            sequenceName = "agenda_seq",
            allocationSize = 1
    )
    private Integer id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(length = 255)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private AgendaStatus status;

    @Column(name = "opening_date", nullable = false)
    private Instant openingDate;

    @Column(name = "voting_duration_seconds", nullable = false)
    private Integer votingDurationSeconds = 60;

    @Column(name = "votes_against")
    private Integer votesAgainst;

    @Column(name = "votes_in_favor")
    private Integer votesInFavor;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private AgendaResult result;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    void prePersist() {
        createdAt = Instant.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    void preUpdate() {
        updatedAt = Instant.now();
    }
}
