CREATE TABLE endereco (
    id_endereco INT NOT NULL AUTO_INCREMENT,
    tipo_logradouro VARCHAR(10),
    logradouro VARCHAR(60),
    numero VARCHAR(10),
    complemento VARCHAR(30),
    cep VARCHAR(8),
    PRIMARY KEY (id_endereco)
);

CREATE TABLE patio (
    id_patio INT NOT NULL AUTO_INCREMENT,
    nome_patio VARCHAR(60) NOT NULL,
    endereco_id_endereco INT NOT NULL,
    PRIMARY KEY (id_patio),
    FOREIGN KEY (endereco_id_endereco) REFERENCES endereco (id_endereco)
);

CREATE TABLE modelo (
    id_modelo INT NOT NULL AUTO_INCREMENT,
    nome_modelo VARCHAR(50) NOT NULL,
    PRIMARY KEY (id_modelo)
);

CREATE TABLE locatario (
    id_locatario INT NOT NULL AUTO_INCREMENT,
    nome_completo VARCHAR(100) NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    endereco_id_endereco INT NOT NULL,
    PRIMARY KEY (id_locatario),
    UNIQUE (cpf),
    FOREIGN KEY (endereco_id_endereco) REFERENCES endereco (id_endereco)
);

CREATE TABLE moto (
    id_moto INT NOT NULL AUTO_INCREMENT,
    placa VARCHAR(7) NOT NULL,
    chassi VARCHAR(17) NOT NULL,
    num_motor VARCHAR(20),
    modelo_id_modelo INT NOT NULL,
    patio_id_patio INT NOT NULL,
    PRIMARY KEY (id_moto),
    UNIQUE (placa),
    UNIQUE (chassi),
    FOREIGN KEY (modelo_id_modelo) REFERENCES modelo (id_modelo),
    FOREIGN KEY (patio_id_patio) REFERENCES patio (id_patio)
);

CREATE TABLE rfid (
    id_rfid INT NOT NULL AUTO_INCREMENT,
    sinal VARCHAR(15) NOT NULL,
    moto_id_moto INT NOT NULL,
    PRIMARY KEY (id_rfid),
    UNIQUE (moto_id_moto),
    FOREIGN KEY (moto_id_moto) REFERENCES moto (id_moto)
);

CREATE TABLE contrato (
    id_contrato INT NOT NULL AUTO_INCREMENT,
    num_contrato VARCHAR(20) NOT NULL,
    data_inicio DATE NOT NULL,
    data_fim DATE,
    moto_id_moto INT NOT NULL,
    locatario_id_locatario INT NOT NULL,
    status ENUM('VIGENTE', 'ARQUIVADO', 'CANCELADO') NOT NULL DEFAULT 'VIGENTE',
    PRIMARY KEY (id_contrato),
    UNIQUE (num_contrato),
    FOREIGN KEY (moto_id_moto) REFERENCES moto (id_moto),
    FOREIGN KEY (locatario_id_locatario) REFERENCES locatario (id_locatario)
);

CREATE TABLE status (
    id_status INT NOT NULL AUTO_INCREMENT,
    data_hora_status TIMESTAMP NOT NULL,
    descricao VARCHAR(25) NOT NULL,
    moto_id_moto INT,
    contrato_id_contrato INT,
    PRIMARY KEY (id_status),
    CHECK (descricao IN ('Liberada', 'Aguardando liberação', 'Em manutenção', 'Irreparável')),
    FOREIGN KEY (moto_id_moto) REFERENCES moto (id_moto),
    FOREIGN KEY (contrato_id_contrato) REFERENCES contrato (id_contrato)
);
