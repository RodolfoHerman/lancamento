CREATE TABLE IF NOT EXISTS lancamento (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    descricao VARCHAR(50) NOT NULL,
    data_vencimento DATE NOT NULL,
    data_pagamento DATE,
    valor DECIMAL(10,2) NOT NULL,
    observacao VARCHAR(100),
    tipo VARCHAR(20) NOT NULL,
    id_categoria BIGINT(20) NOT NULL,
    id_pessoa BIGINT(20) NOT NULL,

    PRIMARY KEY(id),
    FOREIGN KEY(id_categoria) REFERENCES categoria(id),
    FOREIGN KEY(id_pessoa) REFERENCES pessoa(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO lancamento(descricao, data_vencimento, data_pagamento, valor, observacao, tipo, id_categoria, id_pessoa) VALUES("Salário Mensal", "2020-01-15", null, 3243.86, "Pagamento de salário", "RECEITA", 5, 1);
INSERT INTO lancamento(descricao, data_vencimento, data_pagamento, valor, observacao, tipo, id_categoria, id_pessoa) VALUES("Viagem", "2020-01-15", "2020-01-10", 1000.0, "Viagem para Serra do Cipó", "DESPESA", 1, 1);
INSERT INTO lancamento(descricao, data_vencimento, data_pagamento, valor, observacao, tipo, id_categoria, id_pessoa) VALUES("Consórcio", "2020-01-14", "2020-01-11", 618.0, "Compra do carro", "DESPESA", 5, 2);
INSERT INTO lancamento(descricao, data_vencimento, data_pagamento, valor, observacao, tipo, id_categoria, id_pessoa) VALUES("Almoço", "2020-01-18", null, 30.0, null, "DESPESA", 2, 1);
INSERT INTO lancamento(descricao, data_vencimento, data_pagamento, valor, observacao, tipo, id_categoria, id_pessoa) VALUES("Salário Mensal", "2020-01-15", null, 2800.0, "Pagamento de salário", "RECEITA", 5, 2);