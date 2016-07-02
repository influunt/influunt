DELETE FROM `areas`;
DELETE FROM `cidades`;
SET @CidadeId = RANDOM_UUID();
INSERT INTO `cidades` (`id`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@CidadeId, 'São Paulo', NOW(), NOW());
INSERT INTO `areas` (`id`, `descricao`, `cidade_id`, `data_criacao`, `data_atualizacao`) VALUES (RANDOM_UUID(), 51, @CidadeId, NOW(), NOW());
