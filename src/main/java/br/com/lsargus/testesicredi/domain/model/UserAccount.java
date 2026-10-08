package br.com.lsargus.testesicredi.domain.model;

import br.com.lsargus.testesicredi.domain.enuns.Profile;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class UserAccount {
    private UUID id;
    private String name;
    private String cpf;
    private String email;
    private Profile profile;
    private String passwordHash;
    private Instant createdAt;
    private Instant updatedAt;
}
