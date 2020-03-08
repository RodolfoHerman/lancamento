CREATE TABLE IF NOT EXISTS contato (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    id_pessoa BIGINT(20) NOT NULL,
    nome VARCHAR(150) NOT NULL,
    email VARCHAR(100) NOT NULL,
    telefone VARCHAR(25) NOT NULL,

    PRIMARY KEY(id),
    FOREIGN KEY(id_pessoa) REFERENCES pessoa(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO contato(id, id_pessoa, nome, email, telefone) VALUES(1, 1, "Rodolfo", "rodolfo_teste@email.com", "31 99854-1465");