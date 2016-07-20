DELETE FROM `permissoes`;
INSERT INTO `permissoes` (`id`, `chave`, `descricao`, `data_criacao`, `data_atualizacao`) values (RANDOM_UUID(), 'GET /api/v1/cidades', '[Cidade] - Listar Cidades', NOW(), NOW());
