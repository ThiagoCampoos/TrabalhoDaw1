DROP TABLE IF EXISTS sessoes CASCADE;
DROP TABLE IF EXISTS tratamentos CASCADE;
DROP TABLE IF EXISTS fichas_avaliacao CASCADE;
DROP TABLE IF EXISTS clientes CASCADE;
DROP TABLE IF EXISTS usuarios CASCADE;

CREATE TABLE usuarios (
    id UUID PRIMARY KEY,
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
    id UUID PRIMARY KEY,
    area_tratamento VARCHAR(255),
    sessoes_recomendadas INT,
    status VARCHAR(50),
    sessoes_realizadas INTEGER DEFAULT 0,
    data_inicio DATE,
    data_fim_prevista DATE,
    cliente_id UUID NOT NULL REFERENCES clientes(id)
);

CREATE TABLE sessoes (
    id UUID PRIMARY KEY,
    data_sessao DATE,
    protocolo VARCHAR(255),
    valor DECIMAL(10,2),
    eh_reavaliacao BOOLEAN,
    tratamento_id UUID NOT NULL REFERENCES tratamentos(id)
);

CREATE TABLE fichas_avaliacao (
    id UUID PRIMARY KEY,
    fototipo VARCHAR(50),
    alergias VARCHAR(1000),
    consentimento BOOLEAN NOT NULL,
    cliente_id UUID UNIQUE NOT NULL REFERENCES clientes(id)
);