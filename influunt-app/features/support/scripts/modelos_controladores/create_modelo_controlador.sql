DELETE FROM `modelo_controladores`;
DELETE FROM `configuracao_controladores`;
DELETE FROM `fabricantes`;
SET @FabricanteId = RANDOM_UUID();
SET @ConfiguracaoControladorId = RANDOM_UUID();
INSERT INTO `fabricantes` (`id`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@FabricanteId, 'Raro Labs', NOW(), NOW());
INSERT INTO `configuracao_controladores` (`id`, `descricao`, `limite_estagio`, `limite_grupo_semaforico`, `limite_anel`, `limite_detector_pedestre`, `limite_detector_veicular`, `data_criacao`, `data_atualizacao`) VALUES (@ConfiguracaoControladorId, 'Mínima', 1, 1, 1, 1, 1, NOW(), NOW());
INSERT INTO `modelo_controladores` (`id`, `fabricante_id`, `configuracao_id`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES (RANDOM_UUID(), @FabricanteId, @ConfiguracaoControladorId, 'Mínima', NOW(), NOW());
