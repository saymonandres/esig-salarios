DROP TABLE IF EXISTS usuario;
DROP TABLE IF EXISTS pessoa_salario_consolidado;
DROP TABLE IF EXISTS cargo_vencimentos;
DROP TABLE IF EXISTS vencimentos;
DROP TABLE IF EXISTS pessoa;
DROP TABLE IF EXISTS cargo;

CREATE TABLE cargo (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL
);

CREATE TABLE pessoa (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    cidade VARCHAR(255) NOT NULL,
    email VARCHAR(500) NOT NULL,
    cep VARCHAR(15) NOT NULL,
    endereco VARCHAR(500) NOT NULL,
    pais VARCHAR(255) NOT NULL,
    usuario VARCHAR(50) NOT NULL,
    telefone VARCHAR(30) NOT NULL,
    data_nascimento DATE NOT NULL,
    cargo_id BIGINT,
    FOREIGN KEY (cargo_id) REFERENCES cargo(id)
);

CREATE TABLE vencimentos (
    id BIGSERIAL PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL,
    valor NUMERIC(10,2) NOT NULL,
    tipo VARCHAR(10) NOT NULL
);

CREATE TABLE cargo_vencimentos (
    id BIGSERIAL PRIMARY KEY,
    cargo_id BIGINT NOT NULL,
    vencimento_id BIGINT NOT NULL,
    FOREIGN KEY (cargo_id) REFERENCES cargo(id),
    FOREIGN KEY (vencimento_id) REFERENCES vencimentos(id)
);

CREATE TABLE pessoa_salario_consolidado (
    id BIGSERIAL PRIMARY KEY,
    nome_pessoa VARCHAR(255) NOT NULL,
    nome_cargo VARCHAR(255) NOT NULL,
    salario NUMERIC(10,2) NOT NULL,
    pessoa_id BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS usuario (
  id SERIAL PRIMARY KEY,
  pessoa_id BIGINT NOT NULL UNIQUE,
  senha_hash VARCHAR(60) NOT NULL,
  FOREIGN KEY (pessoa_id) REFERENCES pessoa(id)
);

-- Alimenta tabelas a partir dos CSVs
COPY cargo(id, nome)
FROM '/docker-entrypoint-initdb.d/csv/cargo.csv'
DELIMITER ';'
CSV HEADER;
-- Sincroniza a sequência da tabela cargo
SELECT setval('cargo_id_seq', (SELECT MAX(id) FROM cargo));


COPY pessoa(id, nome, cidade, email, cep, endereco, pais, usuario, telefone, data_nascimento, cargo_id)
FROM '/docker-entrypoint-initdb.d/csv/pessoa.csv'
DELIMITER ';'
CSV HEADER;
-- Sincroniza a sequência da tabela pessoa
SELECT setval('pessoa_id_seq', (SELECT MAX(id) FROM pessoa));


COPY vencimentos(id, descricao, valor, tipo)
FROM '/docker-entrypoint-initdb.d/csv/vencimentos.csv'
DELIMITER ';'
CSV HEADER;
-- Sincroniza a sequência da tabela vencimentos
SELECT setval('vencimentos_id_seq', (SELECT MAX(id) FROM vencimentos));


COPY cargo_vencimentos(id, cargo_id, vencimento_id)
FROM '/docker-entrypoint-initdb.d/csv/cargo_vencimentos.csv'
DELIMITER ';'
CSV HEADER;
-- Sincroniza a sequência da tabela cargo_vencimentos
SELECT setval('cargo_vencimentos_id_seq', (SELECT MAX(id) FROM cargo_vencimentos));


INSERT INTO usuario (pessoa_id, senha_hash)
VALUES (1, '$2a$10$PCIaBLJvCmrmESmr8wdTaOGge5SsK45H6AUu9Y4HZHQK47KpvOlLa')
ON CONFLICT (pessoa_id) DO NOTHING;

