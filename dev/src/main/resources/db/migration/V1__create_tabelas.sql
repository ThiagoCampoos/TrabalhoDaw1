CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(60) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(100) NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    ativo BOOLEAN NOT NULL
);

CREATE TABLE clientes (
    id UUID PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    telefone VARCHAR(30),
    data_nascimento DATE
);

CREATE TABLE tratamentos (
    id SERIAL PRIMARY KEY,
    area_tratamento VARCHAR(100) NOT NULL,
    sessoes_recomendadas INT NOT NULL,
    status VARCHAR(60) NOT NULL,
    cliente_id INT NOT NULL REFERENCES usuarios(id),
    sessoes INT NOT NULL
);

CREATE TABLE sessoes (
    id SERIAL PRIMARY KEY,
    data_sessao TIMESTAMP NOT NULL,
    reavaliacao BOOLEAN NOT NULL,
    protocolo VARCHAR(255),
    tratamento_id INT NOT NULL REFERENCES tratamentos(id),
    valor DECIMAL(10,2) NOT NULL
);

CREATE TABLE ficha_avaliacao (
    id SERIAL PRIMARY KEY,
    alergias VARCHAR(255),
    cliente_id INT NOT NULL REFERENCES usuarios(id),
    fototipo VARCHAR(100),
    consentimento BOOLEAN NOT NULL
);