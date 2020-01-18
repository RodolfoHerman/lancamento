CREATE TABLE IF NOT EXISTS pessoa (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    nome VARCHAR(80) NOT NULL,
    ativo BOOLEAN NOT NULL,

    logradouro VARCHAR(255),
    numero VARCHAR(255),
    complemento VARCHAR(255),
    bairro VARCHAR(255),
    cep VARCHAR(255),
    cidade VARCHAR(255),
    estado VARCHAR(255),
    
    PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO pessoa(nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) VALUES("Rodolfo Herman", true, "Rua Águas de Março", "210", "Casa", "Etelvina Carneiro", "31746160", "Belo Horizonte", "Minas Gerais");
INSERT INTO pessoa(nome, ativo, logradouro, numero, complemento, bairro) VALUES("Rafael Felipe", false, "Rua Águas de Março", "210", "Casa", "Etelvina Carneiro");
INSERT INTO pessoa(nome, ativo) VALUES("Ariadne Lara", true);
INSERT INTO pessoa(nome, ativo) VALUES("Mike Herman", false);