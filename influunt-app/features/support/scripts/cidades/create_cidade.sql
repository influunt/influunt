DELETE FROM `transicoes_proibidas`;
DELETE FROM `detectores`;
DELETE FROM `estagios_grupos_semaforicos`;
DELETE FROM `estagios`;
DELETE FROM `tabela_entre_verdes`;
DELETE FROM `grupos_semaforicos`;
DELETE FROM `agrupamentos_aneis`;
DELETE FROM `aneis`;
DELETE FROM `agrupamentos`;
DELETE FROM `controladores`;
DELETE FROM `limite_area`;
DELETE FROM `areas`;
DELETE FROM `cidades`;
INSERT INTO `cidades` (`id`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (RANDOM_UUID(), 'São Paulo', NOW(), NOW());
