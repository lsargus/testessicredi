package br.com.lsargus.testesicredi.domain.service;

import br.com.lsargus.testesicredi.common.exception.NotFoundException;
import br.com.lsargus.testesicredi.common.mapper.AgendaPersistenceMapper;
import br.com.lsargus.testesicredi.domain.enuns.AgendaStatus;
import br.com.lsargus.testesicredi.domain.model.Agenda;
import br.com.lsargus.testesicredi.infrastruct.entity.AgendaEntity;
import br.com.lsargus.testesicredi.infrastruct.repository.AgendaRepository;
import br.com.lsargus.testesicredi.infrastruct.repository.AgendaVoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgendaServiceTest {

    @Mock
    private AgendaRepository repository;

    @Mock
    private AgendaVoteRepository agendaVoteRepository;

    @Mock
    private AgendaPersistenceMapper persistenceMapper;

    @InjectMocks
    private AgendaService agendaService;

    @Test
    void shouldThrowNotFoundExceptionWhenAgendaDoesNotExist() {
        Integer agendaId = 10;

        when(repository.findById(agendaId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> agendaService.getAgenda(agendaId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Pauta nao encontrada");

        verify(repository).findById(agendaId);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldNotSettleAgendaWhenVotingHasNotFinished() {
        Integer agendaId = 10;

        Agenda agenda = new Agenda();
        agenda.setStatus(AgendaStatus.OPEN);
        agenda.setOpeningDate(Instant.now());
        agenda.setVotingDurationSeconds(3600);

        AgendaEntity entity = new AgendaEntity();

        when(repository.findById(agendaId))
                .thenReturn(Optional.of(entity));

        when(persistenceMapper.toDomain(entity))
                .thenReturn(agenda);

        assertThatThrownBy(() -> agendaService.settlement(agendaId))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("A votação ainda não terminou");

        verify(agendaVoteRepository, never())
                .countByAgendaIdAndApproved(anyInt(), anyBoolean());

        verify(repository, never()).saveAndFlush(any());
    }
}
