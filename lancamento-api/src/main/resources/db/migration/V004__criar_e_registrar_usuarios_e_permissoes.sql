CREATE TABLE IF NOT EXISTS usuario (

    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    senha VARCHAR(255) NOT NULL,

    PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS permissao (

    id BIGINT(20) NOT NULL,
    descricao VARCHAR(50) NOT NULL,

    PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS usuario_permissao (

    id_usuario BIGINT(20) NOT NULL,
    id_permissao BIGINT(20) NOT NULL,

    PRIMARY KEY(id_usuario, id_permissao),
    FOREIGN KEY(id_usuario) REFERENCES usuario(id),
    FOREIGN KEY(id_permissao) REFERENCES permissao(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO usuario(nome, email, senha) VALUES('Rodolfo Herman', 'rodolfo@email.com', '$2y$12$sQa4.jgxFZS2jXnBTgqo0u1iRIrJrnFHGvl2vRdCvxmc55ZET0jnO');
INSERT INTO usuario(nome, email, senha) VALUES('Rafael Felipe', 'rafael@email.com', '$2y$12$392/gffYLAM9VViidbANeeUxwDuMmw6zMLTEipovcRN8gUpFJmcNS');

INSERT INTO permissao(id, descricao) VALUES(1, 'ROLE_CADASTRAR_CATEGORIA');
INSERT INTO permissao(id, descricao) VALUES(2, 'ROLE_PESQUISAR_CATEGORIA');

INSERT INTO permissao(id, descricao) VALUES(3, 'ROLE_CADASTRAR_PESSOA');
INSERT INTO permissao(id, descricao) VALUES(4, 'ROLE_REMOVER_PESSOA');
INSERT INTO permissao(id, descricao) VALUES(5, 'ROLE_PESQUISAR_PESSOA');

INSERT INTO permissao(id, descricao) VALUES(6, 'ROLE_CADASTRAR_LANCAMENTO');
INSERT INTO permissao(id, descricao) VALUES(7, 'ROLE_REMOVER_LANCAMENTO');
INSERT INTO permissao(id, descricao) VALUES(8, 'ROLE_PESQUISAR_LANCAMENTO');

INSERT INTO usuario_permissao(id_usuario, id_permissao) VALUES(1,1);
INSERT INTO usuario_permissao(id_usuario, id_permissao) VALUES(1,2);
INSERT INTO usuario_permissao(id_usuario, id_permissao) VALUES(1,3);
INSERT INTO usuario_permissao(id_usuario, id_permissao) VALUES(1,4);
INSERT INTO usuario_permissao(id_usuario, id_permissao) VALUES(1,5);
INSERT INTO usuario_permissao(id_usuario, id_permissao) VALUES(1,6);
INSERT INTO usuario_permissao(id_usuario, id_permissao) VALUES(1,7);
INSERT INTO usuario_permissao(id_usuario, id_permissao) VALUES(1,8);


INSERT INTO usuario_permissao(id_usuario, id_permissao) VALUES(2,2);
INSERT INTO usuario_permissao(id_usuario, id_permissao) VALUES(2,5);
INSERT INTO usuario_permissao(id_usuario, id_permissao) VALUES(2,8);