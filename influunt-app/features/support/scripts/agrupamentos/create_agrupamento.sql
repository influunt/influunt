DELETE FROM `verdes_conflitantes`;
DELETE FROM `tabela_entre_verdes_transicao`;
DELETE FROM `atrasos_de_grupos`;
DELETE FROM `transicoes`;
DELETE FROM `transicoes_proibidas`;
DELETE FROM `estagios_grupos_semaforicos`;
DELETE FROM `detectores`;
DELETE FROM `estagios`;
DELETE FROM `tabela_entre_verdes`;
DELETE FROM `grupos_semaforicos`;
DELETE FROM `enderecos`;
DELETE FROM `aneis`;
DELETE FROM `agrupamentos_controladores`;
DELETE FROM `controladores`;
DELETE FROM `limite_area`;
DELETE FROM `areas`;
DELETE FROM `cidades`;
DELETE FROM `modelo_controladores`;
DELETE FROM `fabricantes`;
DELETE FROM `agrupamentos`;

SET @AgrupamentoId = RANDOM_UUID();

INSERT INTO `cidades`(`id`, `id_json`, `nome`, `data_criacao`, `data_atualizacao`) VALUES
('89f4f1ea-2e2a-430c-abae-56b32982ca0b', NULL, STRINGDECODE('S\u00e3o Paulo'), TIMESTAMP '2016-08-09 11:05:18.096', TIMESTAMP '2016-08-09 11:05:18.096');

INSERT INTO `areas`(`id`, `id_json`, `descricao`, `cidade_id`, `data_criacao`, `data_atualizacao`) VALUES
('cdc81822-2908-43fd-b6d2-a2fe38e67e88', NULL, 1, '89f4f1ea-2e2a-430c-abae-56b32982ca0b', TIMESTAMP '2016-08-09 11:05:18.097', TIMESTAMP '2016-08-09 11:05:18.097');

INSERT INTO `fabricantes`(`id`, `id_json`, `nome`, `data_criacao`, `data_atualizacao`) VALUES
('cd9589b9-4dff-458c-b530-94ffaa309918', NULL, 'Raro Labs', TIMESTAMP '2016-08-09 11:05:18.098', TIMESTAMP '2016-08-09 11:05:18.098');

INSERT INTO `modelo_controladores`(`id`, `id_json`, `fabricante_id`, `descricao`, `limite_estagio`, `limite_grupo_semaforico`, `limite_anel`, `limite_detector_pedestre`, `limite_detector_veicular`, `limite_tabelas_entre_verdes`, `limite_planos`, `data_criacao`, `data_atualizacao`) VALUES
('84e21c9b-169a-4a98-bc69-d0c8689bfcf6', NULL, 'cd9589b9-4dff-458c-b530-94ffaa309918', STRINGDECODE('M\u00ednima'), 16, 16, 4, 4, 8, 2, 16, TIMESTAMP '2016-08-09 11:05:18.1', TIMESTAMP '2016-08-09 11:05:18.1');

INSERT INTO `controladores`(`id`, `id_json`, `nome_endereco`, `status_controlador`, `sequencia`, `numero_smee`, `numero_smeeconjugado1`, `numero_smeeconjugado2`, `numero_smeeconjugado3`, `firmware`, `modelo_id`, `area_id`, `data_criacao`, `data_atualizacao`) VALUES
('e3470d37-55e2-40ee-bb82-b316d76079af', NULL, 'Av. Paulista com R. Bela Cintra', 1, 1, NULL, NULL, NULL, NULL, NULL, '84e21c9b-169a-4a98-bc69-d0c8689bfcf6', 'cdc81822-2908-43fd-b6d2-a2fe38e67e88', TIMESTAMP '2016-08-09 11:05:27.354', TIMESTAMP '2016-08-09 11:05:56.951');

INSERT INTO `aneis`(`id`, `id_json`, `ativo`, `descricao`, `posicao`, `numero_smee`, `controlador_id`, `croqui_id`, `data_criacao`, `data_atualizacao`) VALUES
('42684680-67fa-46d4-8492-3038c37bf9ab', '5029daeb-9cc8-484c-be96-78738ee7a49c', TRUE, NULL, 1, NULL, 'e3470d37-55e2-40ee-bb82-b316d76079af', NULL, TIMESTAMP '2016-08-09 11:05:27.36', TIMESTAMP '2016-08-09 11:05:56.955'),
('329de548-d44f-4315-80e7-aec3369460b3', '2745f0b6-8bf8-4562-8729-90765b8cb7d0', TRUE, NULL, 2, NULL, 'e3470d37-55e2-40ee-bb82-b316d76079af', NULL, TIMESTAMP '2016-08-09 11:05:27.365', TIMESTAMP '2016-08-09 11:05:56.993'),
('b06a3589-36d2-4e6a-a7e2-5918dff4aca0', '1fd59915-e006-466d-a924-f3d1aa414eba', FALSE, NULL, 3, NULL, 'e3470d37-55e2-40ee-bb82-b316d76079af', NULL, TIMESTAMP '2016-08-09 11:05:27.368', TIMESTAMP '2016-08-09 11:05:57.008'),
('4000f989-d277-46f5-b31e-fe0e8d9418ce', '77b77277-44fe-451a-9501-d95650a5bfb8', FALSE, NULL, 4, NULL, 'e3470d37-55e2-40ee-bb82-b316d76079af', NULL, TIMESTAMP '2016-08-09 11:05:27.37', TIMESTAMP '2016-08-09 11:05:57.009');

INSERT INTO `agrupamentos` (`id`, `nome`, `tipo`, `data_criacao`, `data_atualizacao`) VALUES (@AgrupamentoId, 'Corredor da Paulista', 'CORREDOR', NOW(), NOW());
INSERT INTO `agrupamentos_controladores` (`agrupamento_id`, `controlador_id`) VALUES (@AgrupamentoId, 'e3470d37-55e2-40ee-bb82-b316d76079af');
