package br.com.lsargus.testesicredi.domain.service;

import br.com.lsargus.testesicredi.common.exception.NotFoundException;
import br.com.lsargus.testesicredi.common.mapper.AgendaPersistenceMapper;
import br.com.lsargus.testesicredi.domain.enuns.AgendaResult;
import br.com.lsargus.testesicredi.domain.enuns.AgendaStatus;
import br.com.lsargus.testesicredi.domain.model.Agenda;
import br.com.lsargus.testesicredi.infrastruct.repository.AgendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AgendaService {

    private final AgendaRepository repository;

    private final AgendaPersistenceMapper persistenceMapper;

    @Transactional
    public Agenda create(Agenda agenda) {

        agenda.setStatus(AgendaStatus.CREATED);
        agenda.setResult(AgendaResult.INDEFINITE);
        agenda.setVotingDurationSeconds(agenda.getVotingDurationSeconds());
        return persistAgenda(agenda);
    }

    public Agenda update(Integer id, Agenda update, Boolean cancel) {
        Agenda agenda = getAgenda(id);

        agenda.update(update, cancel);

        return persistAgenda(agenda);
    }

    public Agenda openingAgenda(Integer id) {
        Agenda agenda = getAgenda(id);

        agenda.openVoting();

        return persistAgenda(agenda);
    }

    public Agenda settlement(Integer id) {
        Agenda agenda = getAgenda(id);

        agenda.calculateResult();

        return persistAgenda(agenda);
    }

    private Agenda persistAgenda(Agenda agenda) {
        return persistenceMapper.toDomain(repository.saveAndFlush(persistenceMapper.toEntity(agenda)));
    }

    public Agenda getAgenda(Integer id) {
        return persistenceMapper.toDomain(repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pauta nao encontrada")));
    }
}
