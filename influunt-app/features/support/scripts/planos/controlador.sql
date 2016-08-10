DELETE FROM `verdes_conflitantes`;
DELETE FROM `tabela_entre_verdes_transicao`;
DELETE FROM `transicao`;
DELETE FROM `transicoes_proibidas`;
DELETE FROM `detectores`;
DELETE FROM `estagios_grupos_semaforicos`;
DELETE FROM `estagios`;
DELETE FROM `tabela_entre_verdes`;
DELETE FROM `grupos_semaforicos`;
DELETE FROM `enderecos`;
DELETE FROM `aneis`;
DELETE FROM `controladores`;
DELETE FROM `agrupamentos_controladores`;
DELETE FROM `agrupamentos`;
DELETE FROM `areas`;
DELETE FROM `cidades`;
DELETE FROM `modelo_controladores`;
DELETE FROM `fabricantes`;
DELETE FROM `imagens`;

INSERT INTO `cidades`(`id`, `id_json`, `nome`, `data_criacao`, `data_atualizacao`) VALUES
('89f4f1ea-2e2a-430c-abae-56b32982ca0b', NULL, STRINGDECODE('S\u00e3o Paulo'), TIMESTAMP '2016-08-09 11:05:18.096', TIMESTAMP '2016-08-09 11:05:18.096');

INSERT INTO `areas`(`id`, `id_json`, `descricao`, `cidade_id`, `data_criacao`, `data_atualizacao`) VALUES
('cdc81822-2908-43fd-b6d2-a2fe38e67e88', NULL, 1, '89f4f1ea-2e2a-430c-abae-56b32982ca0b', TIMESTAMP '2016-08-09 11:05:18.097', TIMESTAMP '2016-08-09 11:05:18.097');

INSERT INTO `fabricantes`(`id`, `id_json`, `nome`, `data_criacao`, `data_atualizacao`) VALUES
('cd9589b9-4dff-458c-b530-94ffaa309918', NULL, 'Raro Labs', TIMESTAMP '2016-08-09 11:05:18.098', TIMESTAMP '2016-08-09 11:05:18.098');

INSERT INTO `modelo_controladores`(`id`, `id_json`, `fabricante_id`, `descricao`, `data_criacao`, `data_atualizacao`) VALUES
('84e21c9b-169a-4a98-bc69-d0c8689bfcf6', NULL, 'cd9589b9-4dff-458c-b530-94ffaa309918', STRINGDECODE('M\u00ednima'), TIMESTAMP '2016-08-09 11:05:18.1', TIMESTAMP '2016-08-09 11:05:18.1');

INSERT INTO `controladores`(`id`, `id_json`, `nome_endereco`, `status_controlador`, `sequencia`, `numero_smee`, `numero_smeeconjugado1`, `numero_smeeconjugado2`, `numero_smeeconjugado3`, `firmware`, `modelo_id`, `area_id`, `limite_estagio`, `limite_grupo_semaforico`, `limite_anel`, `limite_detector_pedestre`, `limite_detector_veicular`, `limite_tabelas_entre_verdes`, `data_criacao`, `data_atualizacao`) VALUES
('e3470d37-55e2-40ee-bb82-b316d76079af', NULL, 'Av. Paulista com R. Bela Cintra', 1, 1, NULL, NULL, NULL, NULL, NULL, '84e21c9b-169a-4a98-bc69-d0c8689bfcf6', 'cdc81822-2908-43fd-b6d2-a2fe38e67e88', 16, 16, 4, 4, 8, 2, TIMESTAMP '2016-08-09 11:05:27.354', TIMESTAMP '2016-08-09 11:05:56.951');

INSERT INTO `aneis`(`id`, `id_json`, `ativo`, `descricao`, `posicao`, `numero_smee`, `controlador_id`, `croqui_id`, `data_criacao`, `data_atualizacao`) VALUES
('42684680-67fa-46d4-8492-3038c37bf9ab', '5029daeb-9cc8-484c-be96-78738ee7a49c', TRUE, NULL, 1, NULL, 'e3470d37-55e2-40ee-bb82-b316d76079af', NULL, TIMESTAMP '2016-08-09 11:05:27.36', TIMESTAMP '2016-08-09 11:05:56.955'),
('329de548-d44f-4315-80e7-aec3369460b3', '2745f0b6-8bf8-4562-8729-90765b8cb7d0', TRUE, NULL, 2, NULL, 'e3470d37-55e2-40ee-bb82-b316d76079af', NULL, TIMESTAMP '2016-08-09 11:05:27.365', TIMESTAMP '2016-08-09 11:05:56.993'),
('b06a3589-36d2-4e6a-a7e2-5918dff4aca0', '1fd59915-e006-466d-a924-f3d1aa414eba', FALSE, NULL, 3, NULL, 'e3470d37-55e2-40ee-bb82-b316d76079af', NULL, TIMESTAMP '2016-08-09 11:05:27.368', TIMESTAMP '2016-08-09 11:05:57.008'),
('4000f989-d277-46f5-b31e-fe0e8d9418ce', '77b77277-44fe-451a-9501-d95650a5bfb8', FALSE, NULL, 4, NULL, 'e3470d37-55e2-40ee-bb82-b316d76079af', NULL, TIMESTAMP '2016-08-09 11:05:27.37', TIMESTAMP '2016-08-09 11:05:57.009');

INSERT INTO `enderecos`(`id`, `id_json`, `controlador_id`, `anel_id`, `localizacao`, `latitude`, `longitude`, `data_criacao`, `data_atualizacao`) VALUES
('15c96b0c-e978-4041-ab2b-3bde20ced8b3', '2c2722e9-21b4-43eb-a93c-9f0f7fe6a394', NULL, '42684680-67fa-46d4-8492-3038c37bf9ab', 'Av. Paulista', -23.5630684, -46.65443270000003, TIMESTAMP '2016-08-09 11:05:38.35', TIMESTAMP '2016-08-09 11:05:56.991'),
('bba9e786-9af7-47c7-85d1-f1dfb7f14393', 'a788ee64-7f08-4ecc-afad-9774b5c19078', NULL, '42684680-67fa-46d4-8492-3038c37bf9ab', 'R. Bela Cintra', -23.5574614, -46.6629714, TIMESTAMP '2016-08-09 11:05:38.354', TIMESTAMP '2016-08-09 11:05:56.992'),
('df50b804-2d66-48cc-93ba-5801239a38e8', '2d851f52-1c43-4a3b-bd54-976e38cec4b7', NULL, '329de548-d44f-4315-80e7-aec3369460b3', 'Av. Paulista', -23.5630684, -46.65443270000003, TIMESTAMP '2016-08-09 11:05:38.36', TIMESTAMP '2016-08-09 11:05:57.006'),
('5494bc4b-858c-4dce-b2e1-e66038e40cba', 'd4ea3fb1-0e33-49f0-a30d-322ebe2a082f', NULL, '329de548-d44f-4315-80e7-aec3369460b3', 'R. Augusta', -23.559029, -46.661228300000005, TIMESTAMP '2016-08-09 11:05:38.362', TIMESTAMP '2016-08-09 11:05:57.007'),
('714bd503-5cc6-4ad5-a58d-8501f9717f7e', '8f4b898d-50b0-4238-96ec-8b7c8ff45085', 'e3470d37-55e2-40ee-bb82-b316d76079af', NULL, 'Av. Paulista', -23.5630684, -46.65443270000003, TIMESTAMP '2016-08-09 11:05:27.372', TIMESTAMP '2016-08-09 11:05:57.011'),
('49eac119-53f5-4b32-9826-e818d4116dd6', 'db8c26ef-6df9-4a78-a3f1-44f594b47b6c', 'e3470d37-55e2-40ee-bb82-b316d76079af', NULL, 'R. Bela Cintra', -23.5574614, -46.6629714, TIMESTAMP '2016-08-09 11:05:27.375', TIMESTAMP '2016-08-09 11:05:57.012');

INSERT INTO `imagens`(`id`, `id_json`, `filename`, `content_type`, `data_criacao`, `data_atualizacao`) VALUES
('51964ce2-03db-40b4-9aac-993d2fd1697e', 'beb74567-a0b3-4f6e-810b-47336c979d53', 'ubuntu.jpeg', 'image/jpeg', TIMESTAMP '2016-08-09 11:05:33.478', TIMESTAMP '2016-08-09 11:05:33.478'),
('7a9fe32b-48cb-41d1-9672-7de829942244', 'f049a7ee-3655-45b4-8e62-e89e47e87dcc', 'ubuntu.jpeg', 'image/jpeg', TIMESTAMP '2016-08-09 11:05:33.476', TIMESTAMP '2016-08-09 11:05:33.476'),
('7e02909a-60c9-44a4-a041-005603e87d3d', 'f9a2a6f4-fc74-4bd0-8622-23400ce1de96', 'ubuntu.jpeg', 'image/jpeg', TIMESTAMP '2016-08-09 11:05:33.474', TIMESTAMP '2016-08-09 11:05:33.474'),
('c4508094-106e-4aee-a505-2866dc9adfb3', '2894ccc4-4f28-4e19-a8c5-a8f256834691', 'ubuntu.jpeg', 'image/jpeg', TIMESTAMP '2016-08-09 11:05:37.534', TIMESTAMP '2016-08-09 11:05:37.534'),
('bdddf63e-1871-4356-b493-dfa3181e1e00', 'c32b15e9-7f27-4802-8aea-d83809a57ea2', 'ubuntu.jpeg', 'image/jpeg', TIMESTAMP '2016-08-09 11:05:37.632', TIMESTAMP '2016-08-09 11:05:37.632');

INSERT INTO `estagios`(`id`, `id_json`, `imagem_id`, `descricao`, `tempo_maximo_permanencia`, `tempo_maximo_permanencia_ativado`, `posicao`, `demanda_prioritaria`, `anel_id`, `controlador_id`, `detector_id`, `data_criacao`, `data_atualizacao`) VALUES
('921a33ab-5861-46d2-a716-ff8fefc1109e', 'a385829b-5500-4af5-9e32-14cc5486ea43', '51964ce2-03db-40b4-9aac-993d2fd1697e', NULL, 60, TRUE, 1, FALSE, '42684680-67fa-46d4-8492-3038c37bf9ab', NULL, NULL, TIMESTAMP '2016-08-09 11:05:38.345', TIMESTAMP '2016-08-09 11:05:56.988'),
('4f57b2a8-f91a-4d00-8793-efc75717e1e4', '43932869-6df5-4cf0-89cd-38e011bd760a', '7e02909a-60c9-44a4-a041-005603e87d3d', NULL, 60, TRUE, 2, FALSE, '42684680-67fa-46d4-8492-3038c37bf9ab', NULL, NULL, TIMESTAMP '2016-08-09 11:05:38.347', TIMESTAMP '2016-08-09 11:05:56.99'),
('b29f4b27-7086-4828-b858-36be6599866e', 'bce90e7f-3af1-42e3-9660-432895abca54', '7a9fe32b-48cb-41d1-9672-7de829942244', NULL, 60, TRUE, 3, FALSE, '42684680-67fa-46d4-8492-3038c37bf9ab', NULL, NULL, TIMESTAMP '2016-08-09 11:05:38.349', TIMESTAMP '2016-08-09 11:05:56.99'),
('7a987859-23d4-4c6d-83dc-39944a8034c0', '85ba70e4-feb4-4abb-88ee-4a963a3ab3c6', 'c4508094-106e-4aee-a505-2866dc9adfb3', NULL, 60, TRUE, 1, FALSE, '329de548-d44f-4315-80e7-aec3369460b3', NULL, NULL, TIMESTAMP '2016-08-09 11:05:38.357', TIMESTAMP '2016-08-09 11:05:57.004'),
('faae32db-6a19-437e-9c13-554db5c8150c', '4ea98c2a-9b0f-4f59-a04b-3783c2748bc0', 'bdddf63e-1871-4356-b493-dfa3181e1e00', NULL, 60, TRUE, 2, FALSE, '329de548-d44f-4315-80e7-aec3369460b3', NULL, NULL, TIMESTAMP '2016-08-09 11:05:38.359', TIMESTAMP '2016-08-09 11:05:57.005');

INSERT INTO `detectores`(`id`, `id_json`, `tipo`, `anel_id`, `estagio_id`, `controlador_id`, `posicao`, `descricao`, `monitorado`, `tempo_ausencia_deteccao_minima`, `tempo_ausencia_deteccao_maxima`, `tempo_deteccao_permanente_minima`, `tempo_deteccao_permanente_maxima`, `data_criacao`, `data_atualizacao`) VALUES
('ae090808-098b-45f7-9665-bf65fdd2a88e', '38f18630-eef6-4917-8985-933fde07eb12', 'VEICULAR', '42684680-67fa-46d4-8492-3038c37bf9ab', '4f57b2a8-f91a-4d00-8793-efc75717e1e4', NULL, 1, NULL, TRUE, NULL, NULL, NULL, NULL, TIMESTAMP '2016-08-09 11:05:56.958', TIMESTAMP '2016-08-09 11:05:56.958'),
('0efe9cc3-bad0-4fd1-821b-14f6599af0b6', '2972a1dc-9076-4119-a3d3-0aa21694258e', 'PEDESTRE', '42684680-67fa-46d4-8492-3038c37bf9ab', '921a33ab-5861-46d2-a716-ff8fefc1109e', NULL, 1, NULL, TRUE, 0, 0, 0, 0, TIMESTAMP '2016-08-09 11:05:56.959', TIMESTAMP '2016-08-09 11:05:56.959'),
('3d326ba6-e049-46b7-b119-6fc11d5b3484', '9d0e8a59-ec42-4497-829c-b45c684199ad', 'VEICULAR', '42684680-67fa-46d4-8492-3038c37bf9ab', 'b29f4b27-7086-4828-b858-36be6599866e', NULL, 2, NULL, TRUE, NULL, NULL, NULL, NULL, TIMESTAMP '2016-08-09 11:05:56.96', TIMESTAMP '2016-08-09 11:05:56.96');

INSERT INTO `grupos_semaforicos`(`id`, `id_json`, `tipo`, `descricao`, `anel_id`, `controlador_id`, `posicao`, `fase_vermelha_apagada_amarelo_intermitente`, `data_criacao`, `data_atualizacao`) VALUES
('80c18288-b8a2-4791-b8bd-b961927a9882', 'fdc331ac-1a09-4298-b0b9-46faa8bc7f84', 'VEICULAR', NULL, '42684680-67fa-46d4-8492-3038c37bf9ab', NULL, 1, FALSE, TIMESTAMP '2016-08-09 11:05:41.561', TIMESTAMP '2016-08-09 11:05:56.961'),
('2f32a076-e66b-4eaf-9379-fa541b3eeb06', '2e06757c-3199-48a3-8c89-e846cb3b3f3f', 'VEICULAR', NULL, '42684680-67fa-46d4-8492-3038c37bf9ab', NULL, 2, FALSE, TIMESTAMP '2016-08-09 11:05:41.562', TIMESTAMP '2016-08-09 11:05:56.973'),
('8e791e1d-da0c-479d-a2cb-c7a3218969d0', 'd0136a41-700f-4ce6-be70-f012fb170f13', 'PEDESTRE', NULL, '42684680-67fa-46d4-8492-3038c37bf9ab', NULL, 3, FALSE, TIMESTAMP '2016-08-09 11:05:41.564', TIMESTAMP '2016-08-09 11:05:56.981'),
('25db7d58-b518-4a8d-a354-1049da82e184', '4f085d77-1de0-48ca-94f9-13d8e41f661a', 'VEICULAR', NULL, '329de548-d44f-4315-80e7-aec3369460b3', NULL, 4, FALSE, TIMESTAMP '2016-08-09 11:05:41.594', TIMESTAMP '2016-08-09 11:05:56.994'),
('586386a5-b3eb-4fdd-89c1-f4ca5db8619d', 'a65ce90b-f650-42a1-84a9-7e3fda282bbf', 'PEDESTRE', NULL, '329de548-d44f-4315-80e7-aec3369460b3', NULL, 5, FALSE, TIMESTAMP '2016-08-09 11:05:41.595', TIMESTAMP '2016-08-09 11:05:57.0');

INSERT INTO `estagios_grupos_semaforicos`(`id`, `id_json`, `ativo`, `estagio_id`, `grupo_semaforico_id`, `data_criacao`, `data_atualizacao`) VALUES
('97a7e505-51a2-47ab-a518-e7c0ccf4c516', 'a6bba38f-e06d-4cb2-bc59-49784d439304', FALSE, '921a33ab-5861-46d2-a716-ff8fefc1109e', '80c18288-b8a2-4791-b8bd-b961927a9882', TIMESTAMP '2016-08-09 11:05:47.972', TIMESTAMP '2016-08-09 11:05:56.962'),
('6ee11f84-6b59-4728-b701-e72168ca148a', '77c13cd9-61bd-4afa-b73c-f427da7b3140', FALSE, '4f57b2a8-f91a-4d00-8793-efc75717e1e4', '2f32a076-e66b-4eaf-9379-fa541b3eeb06', TIMESTAMP '2016-08-09 11:05:47.994', TIMESTAMP '2016-08-09 11:05:56.974'),
('259edc77-4b67-4c98-9c16-54e1d0a0edbf', '8cdb3f0a-9ed9-431c-8d66-0b818dc860e5', FALSE, 'b29f4b27-7086-4828-b858-36be6599866e', '8e791e1d-da0c-479d-a2cb-c7a3218969d0', TIMESTAMP '2016-08-09 11:05:48.003', TIMESTAMP '2016-08-09 11:05:56.982'),
('1d5286ca-7c8b-474c-93f2-7ce3013da701', '955639f7-122f-4774-802c-ebc5408731c2', FALSE, '7a987859-23d4-4c6d-83dc-39944a8034c0', '25db7d58-b518-4a8d-a354-1049da82e184', TIMESTAMP '2016-08-09 11:05:48.019', TIMESTAMP '2016-08-09 11:05:56.995'),
('05cc8b5b-a2fe-4adc-830e-8709b980d55d', 'da23b7c3-c005-45a7-bda9-0d0c91912db5', FALSE, 'faae32db-6a19-437e-9c13-554db5c8150c', '586386a5-b3eb-4fdd-89c1-f4ca5db8619d', TIMESTAMP '2016-08-09 11:05:48.027', TIMESTAMP '2016-08-09 11:05:57.001');

INSERT INTO `tabela_entre_verdes`(`id`, `id_json`, `descricao`, `grupo_semaforico_id`, `posicao`, `data_criacao`, `data_atualizacao`) VALUES
('65a79af6-13b2-4263-966f-899441e3c2f8', 'a5a63396-a56d-4339-b125-89581acefe99', STRINGDECODE('PADR\u00c3O'), '80c18288-b8a2-4791-b8bd-b961927a9882', 1, TIMESTAMP '2016-08-09 11:05:47.978', TIMESTAMP '2016-08-09 11:05:56.965'),
('21234f51-7337-4681-b773-15e5e161069d', 'c1c9b1d2-fdb6-4552-aa12-075c5b44a07b', STRINGDECODE('PADR\u00c3O'), '2f32a076-e66b-4eaf-9379-fa541b3eeb06', 1, TIMESTAMP '2016-08-09 11:05:47.996', TIMESTAMP '2016-08-09 11:05:56.976'),
('a523f601-8d89-48fe-a9d5-00973ab680b6', '1c7d33e7-3cb8-491b-ab0c-7559255a170a', STRINGDECODE('PADR\u00c3O'), '8e791e1d-da0c-479d-a2cb-c7a3218969d0', 1, TIMESTAMP '2016-08-09 11:05:48.004', TIMESTAMP '2016-08-09 11:05:56.983'),
('319504a5-1eb3-4b39-ae9d-0ef687a75dfe', '83806ec1-55c8-44f5-a0fc-dce9bf13d0c7', STRINGDECODE('PADR\u00c3O'), '25db7d58-b518-4a8d-a354-1049da82e184', 1, TIMESTAMP '2016-08-09 11:05:48.021', TIMESTAMP '2016-08-09 11:05:56.997'),
('696c0856-a8ea-4c1e-a76a-01d09023bc98', 'dd631cba-b8a0-4948-8306-f3d8605fa297', STRINGDECODE('PADR\u00c3O'), '586386a5-b3eb-4fdd-89c1-f4ca5db8619d', 1, TIMESTAMP '2016-08-09 11:05:48.028', TIMESTAMP '2016-08-09 11:05:57.002');

INSERT INTO `transicao`(`id`, `id_json`, `grupo_semaforico_id`, `origem_id`, `destino_id`, `destroy`, `data_criacao`, `data_atualizacao`) VALUES
('1bae4c2f-3308-4080-b599-fbf591f92377', '6822541a-a180-4651-b564-85d0ac897a7c', '80c18288-b8a2-4791-b8bd-b961927a9882', '921a33ab-5861-46d2-a716-ff8fefc1109e', '4f57b2a8-f91a-4d00-8793-efc75717e1e4', FALSE, TIMESTAMP '2016-08-09 11:05:47.984', TIMESTAMP '2016-08-09 11:05:56.971'),
('ae9f1d5b-4435-4e66-b92d-77873a381736', '8aa5adad-1a27-4c3e-952a-41af3d629eeb', '80c18288-b8a2-4791-b8bd-b961927a9882', '921a33ab-5861-46d2-a716-ff8fefc1109e', 'b29f4b27-7086-4828-b858-36be6599866e', FALSE, TIMESTAMP '2016-08-09 11:05:47.991', TIMESTAMP '2016-08-09 11:05:56.972'),
('53cd4c4e-0e74-49cd-8549-c353ac66cf7b', 'ded443b1-d1fa-4f49-b6f8-331541b0594b', '2f32a076-e66b-4eaf-9379-fa541b3eeb06', '4f57b2a8-f91a-4d00-8793-efc75717e1e4', '921a33ab-5861-46d2-a716-ff8fefc1109e', FALSE, TIMESTAMP '2016-08-09 11:05:47.997', TIMESTAMP '2016-08-09 11:05:56.979'),
('a234ddf3-f1d2-49b7-a068-ee67c7d5e896', '34a36e83-13cb-4c76-8d53-4c828d751a84', '2f32a076-e66b-4eaf-9379-fa541b3eeb06', '4f57b2a8-f91a-4d00-8793-efc75717e1e4', 'b29f4b27-7086-4828-b858-36be6599866e', FALSE, TIMESTAMP '2016-08-09 11:05:47.998', TIMESTAMP '2016-08-09 11:05:56.981'),
('56baa6d6-3228-449c-a811-9726db9aff64', 'a65858d1-970d-42b8-9b66-43e7497be7be', '8e791e1d-da0c-479d-a2cb-c7a3218969d0', 'b29f4b27-7086-4828-b858-36be6599866e', '921a33ab-5861-46d2-a716-ff8fefc1109e', FALSE, TIMESTAMP '2016-08-09 11:05:48.007', TIMESTAMP '2016-08-09 11:05:56.986'),
('ee6e7f69-b17a-4cdb-84a3-5cc588581170', 'cfecaad6-1e72-45ca-af90-70b509e9ec17', '8e791e1d-da0c-479d-a2cb-c7a3218969d0', 'b29f4b27-7086-4828-b858-36be6599866e', '4f57b2a8-f91a-4d00-8793-efc75717e1e4', FALSE, TIMESTAMP '2016-08-09 11:05:48.009', TIMESTAMP '2016-08-09 11:05:56.987'),
('19a31e91-586d-4334-a626-8c10a47b08e2', 'b371cd2e-ef35-46c3-943c-9cbc37bb67df', '25db7d58-b518-4a8d-a354-1049da82e184', '7a987859-23d4-4c6d-83dc-39944a8034c0', 'faae32db-6a19-437e-9c13-554db5c8150c', FALSE, TIMESTAMP '2016-08-09 11:05:48.024', TIMESTAMP '2016-08-09 11:05:56.999'),
('b9f59699-e762-4720-b79e-4d5c32272526', '58aaa98f-78a4-4bf5-8a25-548564dd68a3', '586386a5-b3eb-4fdd-89c1-f4ca5db8619d', 'faae32db-6a19-437e-9c13-554db5c8150c', '7a987859-23d4-4c6d-83dc-39944a8034c0', FALSE, TIMESTAMP '2016-08-09 11:05:48.031', TIMESTAMP '2016-08-09 11:05:57.003');

INSERT INTO `tabela_entre_verdes_transicao`(`id`, `id_json`, `tabela_entre_verdes_id`, `transicao_id`, `tempo_amarelo`, `tempo_vermelho_intermitente`, `tempo_vermelho_limpeza`, `tempo_atraso_grupo`, `data_criacao`, `data_atualizacao`) VALUES
('0a8601e3-aaa2-4999-b99a-5d328a327505', 'e8d17b9c-d3bf-4683-97fa-d4a4cce8648d', '65a79af6-13b2-4263-966f-899441e3c2f8', '1bae4c2f-3308-4080-b599-fbf591f92377', 3, NULL, 0, 0, TIMESTAMP '2016-08-09 11:05:47.979', TIMESTAMP '2016-08-09 11:05:56.967'),
('8870f2d5-7806-4929-9cd3-b40e9d5d0e21', 'a6a6d9a8-bc11-4a1e-9bcd-d9fd8e955cf5', '65a79af6-13b2-4263-966f-899441e3c2f8', 'ae9f1d5b-4435-4e66-b92d-77873a381736', 5, NULL, 2, 15, TIMESTAMP '2016-08-09 11:05:47.981', TIMESTAMP '2016-08-09 11:05:56.969'),
('396c8c0d-a84e-48a7-b776-86efe55631a9', '514ce9c3-2a20-4660-812f-7baf888c18cc', '21234f51-7337-4681-b773-15e5e161069d', '53cd4c4e-0e74-49cd-8549-c353ac66cf7b', 3, NULL, 0, 0, TIMESTAMP '2016-08-09 11:05:47.996', TIMESTAMP '2016-08-09 11:05:56.977'),
('daa8fe98-93d9-4f08-b18a-005dc8e55b2e', '3b13907b-23cb-42ca-ab59-fa30cf51ad0f', '21234f51-7337-4681-b773-15e5e161069d', 'a234ddf3-f1d2-49b7-a068-ee67c7d5e896', 3, NULL, 0, 0, TIMESTAMP '2016-08-09 11:05:47.997', TIMESTAMP '2016-08-09 11:05:56.978'),
('51218675-3090-4276-ac09-396646e481d4', 'e4b0fdca-8977-4f06-b0c5-0ff28063ae36', 'a523f601-8d89-48fe-a9d5-00973ab680b6', '56baa6d6-3228-449c-a811-9726db9aff64', NULL, 3, 0, 0, TIMESTAMP '2016-08-09 11:05:48.004', TIMESTAMP '2016-08-09 11:05:56.983'),
('0a5615bb-d51c-4723-b47f-39e164b6b016', '7c3d7e93-dbee-4893-a95f-9c7eebe7eba5', 'a523f601-8d89-48fe-a9d5-00973ab680b6', 'ee6e7f69-b17a-4cdb-84a3-5cc588581170', NULL, 3, 0, 0, TIMESTAMP '2016-08-09 11:05:48.005', TIMESTAMP '2016-08-09 11:05:56.986'),
('338bea79-27fd-4d0c-b8dd-072c227180ac', '7d1b457f-f0f6-48ab-a44c-76713188fed8', '319504a5-1eb3-4b39-ae9d-0ef687a75dfe', '19a31e91-586d-4334-a626-8c10a47b08e2', 3, NULL, 0, 0, TIMESTAMP '2016-08-09 11:05:48.022', TIMESTAMP '2016-08-09 11:05:56.998'),
('0000af44-7643-4126-961c-0f736f300de6', '1b39944f-efe3-4e79-96b2-3cc73b493c9a', '696c0856-a8ea-4c1e-a76a-01d09023bc98', 'b9f59699-e762-4720-b79e-4d5c32272526', NULL, 3, 0, 0, TIMESTAMP '2016-08-09 11:05:48.03', TIMESTAMP '2016-08-09 11:05:57.002');

INSERT INTO `verdes_conflitantes`(`id`, `id_json`, `origem_id`, `destino_id`, `data_criacao`, `data_atualizacao`) VALUES
('f0846f07-3176-47e4-a2ec-b61453ca63b8', '066a87fd-ae85-450b-a495-f5244555d296', '80c18288-b8a2-4791-b8bd-b961927a9882', '2f32a076-e66b-4eaf-9379-fa541b3eeb06', TIMESTAMP '2016-08-09 11:05:45.393', TIMESTAMP '2016-08-09 11:05:56.964'),
('f479e506-e9b7-4263-9a21-6efcdc3806ac', '77d688ff-e26f-41e4-87e5-25cd86dd0c93', '2f32a076-e66b-4eaf-9379-fa541b3eeb06', '8e791e1d-da0c-479d-a2cb-c7a3218969d0', TIMESTAMP '2016-08-09 11:05:45.396', TIMESTAMP '2016-08-09 11:05:56.975'),
('024f61cf-a9ab-4fd7-9fe9-5b1a978046d0', 'f957b433-7e92-4b31-940d-7dda8e6730d1', '25db7d58-b518-4a8d-a354-1049da82e184', '586386a5-b3eb-4fdd-89c1-f4ca5db8619d', TIMESTAMP '2016-08-09 11:05:45.415', TIMESTAMP '2016-08-09 11:05:56.996');
