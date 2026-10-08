CREATE TABLE user_account (
    id UUID PRIMARY KEY,
    name VARCHAR(120) NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    email VARCHAR(255) NOT NULL,
    profile VARCHAR(10) NOT NULL DEFAULT 'USER',
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT uk_user_cpf UNIQUE (cpf),
    CONSTRAINT uk_user_email UNIQUE (email),
    CONSTRAINT ck_user_profile CHECK (profile IN ('ADMIN', 'USER')),
    CONSTRAINT ck_user_cpf_format CHECK (cpf ~ '^[0-9]{11}$')
);

INSERT INTO user_account
(
    id,
    name,
    cpf,
    email,
    password_hash,
    profile,
    created_at,
    updated_at
)
VALUES
    (
        '1cbef51b-19b2-4df0-a8b6-7d4ef6a0a001',
        'Administrador',
        '12345678909',
        'admin@teste.com',
        '$2a$10$75l2YltpI16jMQ/xo87DjOdqqTnDVO9/I.lebjP6TCMrYJv/lZow6',
        'ADMIN',
        now(),
        now()
    );