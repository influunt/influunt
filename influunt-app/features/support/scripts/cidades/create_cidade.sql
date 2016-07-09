DELETE FROM `areas`;
DELETE FROM `cidades`;
INSERT INTO `cidades` (`id`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (RANDOM_UUID(), 'São Paulo', NOW(), NOW());
