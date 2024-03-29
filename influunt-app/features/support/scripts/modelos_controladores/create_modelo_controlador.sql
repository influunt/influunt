DELETE FROM `verdes_conflitantes`;
DELETE FROM `tabela_entre_verdes_transicao`;
DELETE FROM `atrasos_de_grupos`;
DELETE FROM `transicoes`;
DELETE FROM `transicoes_proibidas`;
DELETE FROM `detectores`;
DELETE FROM `grupos_semaforicos_planos`;
DELETE FROM `estagios_grupos_semaforicos`;
DELETE FROM `estagios_planos`;
DELETE FROM `planos`;
DELETE FROM `estagios`;
DELETE FROM `tabela_entre_verdes`;
DELETE FROM `grupos_semaforicos`;
DELETE FROM `enderecos`;
DELETE FROM `versoes_planos`;
DELETE FROM `agrupamentos_aneis`;
DELETE FROM `aneis`;
DELETE FROM `versoes_controladores`;
DELETE FROM `controladores_fisicos`;
DELETE FROM `controladores`;
DELETE FROM `agrupamentos`;
DELETE FROM `limite_area`;
DELETE FROM `areas`;
DELETE FROM `cidades`;
DELETE FROM `modelo_controladores`;
DELETE FROM `fabricantes`;
DELETE FROM `imagens`;
DELETE FROM `faixas_de_valores`;

SET @FabricanteId = RANDOM_UUID();
INSERT INTO `fabricantes`(`id`, `id_json`, `nome`, `data_criacao`, `data_atualizacao`) VALUES
('cd9589b9-4dff-458c-b530-94ffaa309918', NULL, 'Raro Labs', TIMESTAMP '2016-08-09 11:05:18.098', TIMESTAMP '2016-08-09 11:05:18.098');
INSERT INTO `modelo_controladores`(`id`, `id_json`, `fabricante_id`, `descricao`, `limite_estagio`, `limite_grupo_semaforico`, `limite_anel`, `limite_detector_pedestre`, `limite_detector_veicular`, `limite_tabelas_entre_verdes`, `limite_planos`, `data_criacao`, `data_atualizacao`) VALUES
('84e21c9b-169a-4a98-bc69-d0c8689bfcf6', NULL, 'cd9589b9-4dff-458c-b530-94ffaa309918', STRINGDECODE('M\u00ednima'), 16, 16, 4, 4, 8, 2, 16, TIMESTAMP '2016-08-09 11:05:18.1', TIMESTAMP '2016-08-09 11:05:18.1');
