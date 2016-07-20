DELETE FROM `perfis`;
INSERT INTO `perfis` (`id`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (RANDOM_UUID(), 'São Paulo', NOW(), NOW());
