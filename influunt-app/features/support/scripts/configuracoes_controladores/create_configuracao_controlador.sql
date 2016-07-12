DELETE FROM `transicoes_proibidas`;
DELETE FROM `estagios_grupos_semaforicos`;
DELETE FROM `estagios`;
DELETE FROM `tabela_entre_verdes`;
DELETE FROM `grupos_semaforicos`;
DELETE FROM `aneis`;
DELETE FROM `agrupamentos_controladores`;
DELETE FROM `agrupamentos`;
DELETE FROM `controladores`;
DELETE FROM `modelo_controladores`;
DELETE FROM `configuracao_controladores`;
INSERT INTO `configuracao_controladores` (`id`, `descricao`, `limite_estagio`, `limite_grupo_semaforico`, `limite_anel`, `limite_detector_pedestre`, `limite_detector_veicular`, `data_criacao`, `data_atualizacao`) VALUES (RANDOM_UUID(), 'desc', '1', '1', '1', '1', '1', NOW(), NOW());

