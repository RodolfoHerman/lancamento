CREATE TABLE IF NOT EXISTS categoria (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL,

    PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO categoria(nome) VALUES("Lazer");
INSERT INTO categoria(nome) VALUES("Alimentação");
INSERT INTO categoria(nome) VALUES("Supermercado");
INSERT INTO categoria(nome) VALUES("Farmácia");
INSERT INTO categoria(nome) VALUES("Outros");