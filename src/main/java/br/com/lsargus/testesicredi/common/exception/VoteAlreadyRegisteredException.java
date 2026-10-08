package br.com.lsargus.testesicredi.common.exception;

public class VoteAlreadyRegisteredException extends RuntimeException {
    public VoteAlreadyRegisteredException() {
        super("O associado já registrou um voto para esta pauta.");
    }

    public VoteAlreadyRegisteredException(String message) {
        super(message);
    }
}
