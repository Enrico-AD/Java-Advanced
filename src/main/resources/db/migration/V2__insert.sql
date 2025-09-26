INSERT INTO modelo (
    id_modelo,
    nome_modelo
) VALUES ( 1,
           'Sport' );

INSERT INTO modelo (
    id_modelo,
    nome_modelo
) VALUES ( 2,
           'E' );

INSERT INTO modelo (
    id_modelo,
    nome_modelo
) VALUES ( 3,
           'Pop' );

INSERT INTO endereco (
    id_endereco,
    tipo_logradouro,
    logradouro,
    numero,
    complemento,
    cep
) VALUES
(1,  'Rua',      'das Acácias',   '100',  'Bloco A',     '01001000'),
(2,  'Avenida',  'Brasil',        '2500', NULL,          '01413000'),
(3,  'Praça',    'da Sé',         '1',    'Lado Par',    '01001001'),
(4,  'Rua',      'Augusta',       '900',  'Loja 10',     '01304001'),
(5,  'Alameda',  'Santos',        '1120', 'Conjunto 3',  '01419001'),
(6,  'Rua',      'Vergueiro',     '1500', '5 Andar',     '01504000'),
(7,  'Avenida',  'Paulista',      '1800', 'Sala 88',     '01310200'),
(8,  'Rua',      'Oscar Freire',  '555',  NULL,          '01426001'),
(9,  'Avenida',  'Faria Lima',    '4055', 'Torre Norte', '04543011'),
(10, 'Rua',      'dos Pinheiros', '700',  'Casa 2',      '05422011'),
(11, 'Rua',      'Afonso Brás',   '50',   'Apto 101',    '04511011'),
(12, 'Avenida',  'Ibirapuera',    '3000', 'Apto 202',    '04029200'),
(13, 'Rua',      'Helena',        '235',  'Apto 303',    '04552050'),
(14, 'Avenida',  'Rebouças',      '600',  'Apto 404',    '05402000'),
(15, 'Rua',      'Turiassu',      '1200', 'Apto 505',    '05005001'),
(16, 'Rua',      'Itacolomi',     '450',  'Apto 606',    '01239020'),
(17, 'Avenida',  'Angélica',      '2200', 'Apto 707',    '01228200'),
(18, 'Rua',      'Bela Cintra',   '1100', 'Apto 808',    '01415001'),
(19, 'Rua',      'Haddock Lobo',  '800',  'Apto 909',    '01414002'),
(20, 'Avenida',  'Nove de Julho', '5000', 'Apto 1010',   '01406200'),
(21, 'Avenida',  'Nove de Julho', '5000', 'Apto 1010',   '01406200');


INSERT INTO patio (
    id_patio,
    nome_patio,
    endereco_id_endereco
)
VALUES
( 1, 'Pátio Central SP',1 ),
( 2, 'Pátio Zona Sul', 2 ),
( 3, 'Pátio Zona Leste', 3 ),
( 4, 'Pátio Zona Oeste', 4 ),
( 5, 'Pátio Zona Norte', 5 ),
( 6, 'Pátio Guarulhos', 6 ),
( 7, 'Pátio Santo André', 7 ),
( 8, 'Pátio São Bernardo', 8 ),
( 9, 'Pátio Osasco', 9 ),
( 10, 'Pátio Campinas', 10 );

INSERT INTO locatario (
    id_locatario,
    nome_completo,
    cpf,
    endereco_id_endereco
) VALUES
(1,  'Carlos Alberto Souza',     '11122233344', 11),
(2,  'Maria Eduarda Ferreira',   '22233344455', 12),
(3,  'José Ricardo Lima',        '33344455566', 13),
(4,  'Ana Carolina Martins',     '44455566677', 14),
(5,  'Lucas Gabriel Pereira',    '55566677788', 15),
(6,  'Juliana Santos Costa',     '66677788899', 16),
(7,  'Fernando Oliveira Dias',   '77788899900', 17),
(8,  'Beatriz Almeida Rocha',    '88899900011', 18),
(9,  'Rafael Mendes Barros',     '99900011122', 19),
(10, 'Larissa Gonçalves Neves',  '00011122233', 20),
(11, 'Rogério Alvez',            '00011134356', 21);

INSERT INTO moto (
    id_moto,
    placa,
    chassi,
    num_motor,
    modelo_id_modelo,
    patio_id_patio
) VALUES
(1,  'RST0A01', '9C6KSRT0A01ABCDE', 'MTR001', 1, 1),
(2,  'POP1B12', '9C6KPOP1B12FGHIJ', 'MTR002', 3, 2),
(3,  'ELT2C23', '9C6KELT2C23KLMNO', 'MTR003', 2, 1),
(4,  'RST3D34', '9C6KRST3D34PQRST', 'MTR004', 1, 3),
(5,  'POP4E45', '9C6KPOP4E45UVWXY', 'MTR005', 3, 4),
(6,  'ELT5F56', '9C6KELT5F56ZABCD', 'MTR006', 2, 5),
(7,  'RST6G67', '9C6KRST6G67EFGHI', 'MTR007', 1, 2),
(8,  'POP7H78', '9C6KPOP7H78JKLMN', 'MTR008', 3, 3),
(9,  'ELT8I89', '9C6KELT8I89OPQRS', 'MTR009', 2, 4),
(10, 'RST9J90', '9C6KRST9J90TUVWX', 'MTR010', 1, 5);

 INSERT INTO rfid (
     id_rfid,
     sinal,
     moto_id_moto
 ) VALUES
 (1,  'A1B2C3D4E5F0001', 1),
 (2,  'A1B2C3D4E5F0002', 2),
 (3,  'A1B2C3D4E5F0003', 3),
 (4,  'A1B2C3D4E5F0004', 4),
 (5,  'A1B2C3D4E5F0005', 5),
 (6,  'A1B2C3D4E5F0006', 6),
 (7,  'A1B2C3D4E5F0007', 7),
 (8,  'A1B2C3D4E5F0008', 8),
 (9,  'A1B2C3D4E5F0009', 9),
 (10, 'A1B2C3D4E5F0010', 10);

INSERT INTO contrato (
    id_contrato,
    num_contrato,
    data_inicio,
    data_fim,
    moto_id_moto,
    locatario_id_locatario,
    status
) VALUES
(1,  'CTR-2025-001', '2025-01-10', '2026-01-09', 1, 1, 'VIGENTE'),
(2,  'CTR-2025-002', '2025-02-15', NULL,         2, 2, 'VIGENTE'),
(3,  'CTR-2025-003', '2025-03-20', '2026-03-19', 3, 3, 'VIGENTE'),
(4,  'CTR-2025-004', '2025-04-25', NULL,         4, 4, 'VIGENTE'),
(5,  'CTR-2025-005', '2025-05-30', '2026-05-29', 5, 5, 'VIGENTE'),
(6,  'CTR-2025-006', '2025-06-01', NULL,         6, 6, 'VIGENTE'),
(7,  'CTR-2025-007', '2025-07-05', '2026-07-04', 7, 7, 'VIGENTE'),
(8,  'CTR-2025-008', '2025-08-10', NULL,         8, 8, 'VIGENTE'),
(9,  'CTR-2025-009', '2025-09-15', '2026-09-14', 9, 9, 'VIGENTE'),
(10, 'CTR-2025-010', '2025-09-20', NULL,         10, 10, 'VIGENTE'),
(11, 'CTR-2025-011', '2025-09-20', NULL,         10, 11, 'VIGENTE');

INSERT INTO status (
    id_status,
    data_hora_status,
    descricao,
    moto_id_moto,
    contrato_id_contrato
) VALUES
(1,  '2025-09-10 08:00:00', 'Aguardando liberação', 1,  1),
(2,  '2025-09-11 14:30:00', 'Liberada',              1,  11),
(3,  '2025-09-12 09:00:00', 'Liberada',              NULL, 1),
(4,  '2025-09-13 10:00:00', 'Aguardando liberação',  2,  3),
(5,  '2025-09-14 11:00:00', 'Em manutenção',         2,  4),
(6,  '2025-09-15 17:45:00', 'Liberada',              2,  5),
(7,  '2025-09-16 12:00:00', 'Liberada',              NULL, 2),
(8,  '2025-09-17 13:00:00', 'Aguardando liberação',  3,  6),
(9,  '2025-09-18 14:00:00', 'Liberada',              3,  7),
(10, '2025-09-19 15:00:00', 'Em manutenção',         4,  8),
(11, '2025-09-20 16:00:00', 'Irreparável',           4,  9),
(12, '2025-09-21 09:30:00', 'Liberada',              5,  10);
