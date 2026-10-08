package br.com.lsargus.testesicredi.domain.service;

import br.com.lsargus.testesicredi.common.exception.VoteAlreadyRegisteredException;
import br.com.lsargus.testesicredi.common.mapper.AgendaVotePersistenceMapper;
import br.com.lsargus.testesicredi.domain.model.Agenda;
import br.com.lsargus.testesicredi.domain.model.AgendaVote;
import br.com.lsargus.testesicredi.domain.model.UserAccount;
import br.com.lsargus.testesicredi.infrastruct.entity.AgendaVoteEntity;
import br.com.lsargus.testesicredi.infrastruct.repository.AgendaVoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final UserAccountService userService;
    private final AgendaService agendaService;
    private final AgendaVoteRepository repository;

    private final AgendaVotePersistenceMapper persistenceMapper;

    public void computeVote(Integer agendaId, UUID userId, boolean requestApproved) {
        Agenda agenda = agendaService.getAgenda(agendaId);
        UserAccount user = userService.getUserAccount(userId);

        if (repository.existsAgendaVoteEntityByAgenda_IdAndUser_Id(agendaId, userId)) {
            throw new VoteAlreadyRegisteredException();
        }

        var vote = AgendaVote.builder().user(user).agenda(agenda).approved(requestApproved).build();

        AgendaVoteEntity voteEntity = persistenceMapper.toEntity(vote);
        repository.saveAndFlush(voteEntity);

    }
}
