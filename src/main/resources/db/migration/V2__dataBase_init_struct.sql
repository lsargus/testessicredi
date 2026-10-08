CREATE TABLE agenda (
    id INTEGER PRIMARY KEY,
    name VARCHAR(120) NOT NULL,
    description VARCHAR(255) NULL,
    status VARCHAR(10) NOT NULL,
    opening_date TIMESTAMP WITH TIME ZONE NULL,
    voting_duration_seconds INTEGER NOT NULL DEFAULT 60,
    votes_against INTEGER NULL,
    votes_in_favor INTEGER NULL,
    result VARCHAR(10) NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT ck_agenda_status CHECK (status IN ('CREATED', 'CANCELLED', 'OPEN', 'COMPLETED')),
    CONSTRAINT ck_agenda_result CHECK (result IN ('APPROVED', 'REJECTED', 'INDEFINITE'))
);

CREATE TABLE agenda_votes (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    agenda_id INTEGER NOT NULL,
    approved BIT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT fk_vote_user FOREIGN KEY (user_id) references user_account(id),
    CONSTRAINT fk_vote_agenda FOREIGN KEY (agenda_id) references agenda(id)
);

CREATE INDEX idx_vote_user_id ON agenda_votes(user_id);
CREATE INDEX idx_vote_agenda_id ON agenda_votes(agenda_id);