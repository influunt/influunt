DELETE FROM `verdes_conflitantes`;
DELETE FROM `transicao`;
DELETE FROM `transicoes_proibidas`;
DELETE FROM `estagios_grupos_semaforicos`;
DELETE FROM `estagios`;
DELETE FROM `tabela_entre_verdes`;
DELETE FROM `grupos_semaforicos`;
DELETE FROM `aneis`;
DELETE FROM `agrupamentos_controladores`;
DELETE FROM `agrupamentos`;
DELETE FROM `controladores`;
DELETE FROM `areas`;
DELETE FROM `cidades`;
DELETE FROM `modelo_controladores`;
DELETE FROM `configuracao_controladores`;
DELETE FROM `fabricantes`;
SET @FabricanteId = RANDOM_UUID();
SET @ConfiguracaoControladorId = RANDOM_UUID();
SET @CidadeId = RANDOM_UUID();
INSERT INTO `cidades` (`id`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@CidadeId, 'São Paulo', NOW(), NOW());
INSERT INTO `areas` (`id`, `descricao`, `cidade_id`, `data_criacao`, `data_atualizacao`) VALUES (RANDOM_UUID(), 1, @CidadeId, NOW(), NOW());
INSERT INTO `fabricantes` (`id`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@FabricanteId, 'Raro Labs', NOW(), NOW());
INSERT INTO `configuracao_controladores` (`id`, `descricao`, `limite_estagio`, `limite_grupo_semaforico`, `limite_anel`, `limite_detector_pedestre`, `limite_detector_veicular`, `data_criacao`, `data_atualizacao`) VALUES (@ConfiguracaoControladorId, 'Mínima', 4, 4, 2, 1, 1, NOW(), NOW());
INSERT INTO `modelo_controladores` (`id`, `fabricante_id`, `configuracao_id`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES (RANDOM_UUID(), @FabricanteId, @ConfiguracaoControladorId, 'Mínima', NOW(), NOW());
