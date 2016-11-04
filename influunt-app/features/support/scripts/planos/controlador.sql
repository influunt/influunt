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
DELETE FROM `eventos`;
UPDATE `versoes_tabelas_horarias` SET tabela_horaria_origem_id = NULL;
DELETE FROM `tabela_horarios`;
DELETE FROM `versoes_tabelas_horarias`;
DELETE FROM `versoes_planos`;
DELETE FROM `agrupamentos_aneis`;
DELETE FROM `aneis`;
DELETE FROM `versoes_controladores`;
DELETE FROM `controladores_fisicos`;
DELETE FROM `controladores`;
DELETE FROM `agrupamentos`;
DELETE FROM `limite_area`;
UPDATE usuarios SET area_id=NULL WHERE login='mobilab';
DELETE FROM `areas`;
DELETE FROM `cidades`;
DELETE FROM `modelo_controladores`;
DELETE FROM `fabricantes`;
DELETE FROM `imagens`;
DELETE FROM `faixas_de_valores`;

SET @UsurioId = (SELECT id FROM "usuarios" where login = 'mobilab');

INSERT INTO `cidades` (`id`, `id_json`, `nome`, `data_criacao`, `data_atualizacao`)
VALUES
  ('24b4e422-8661-11e6-9678-1f0de452edaf','24b792c6-8661-11e6-9678-1f0de452edaf','São Paulo',NOW(),NOW());

INSERT INTO `areas` (`id`, `id_json`, `descricao`, `cidade_id`, `data_criacao`, `data_atualizacao`)
VALUES
  ('24bc8524-8661-11e6-9678-1f0de452edaf','24bc8538-8661-11e6-9678-1f0de452edaf',1,'24b4e422-8661-11e6-9678-1f0de452edaf','2016-09-29 13:24:06.000000','2016-09-29 13:24:06.000000');

UPDATE usuarios SET area_id='24bc8524-8661-11e6-9678-1f0de452edaf' WHERE login='mobilab';

INSERT INTO `fabricantes` (`id`, `id_json`, `nome`, `data_criacao`, `data_atualizacao`)
VALUES
  ('24bf38a0-8661-11e6-9678-1f0de452edaf','24c439ea-8661-11e6-9678-1f0de452edaf','Raro Labs','2016-09-29 13:24:06.000000','2016-09-29 13:24:06.000000');

INSERT INTO `imagens` (`id`, `id_json`, `filename`, `content_type`, `data_criacao`, `data_atualizacao`)
VALUES
  ('4caff4b3-c713-40f2-8840-ba342f7940bf','f27dfee4-19ed-4f04-9c62-8824e797181f','sinal.png','image/png','2016-09-29 13:25:53.817000','2016-09-29 13:25:53.817000'),
  ('9389d305-6bf4-40b6-b384-e2b75fb4195d','77ec2e2d-5786-4f11-918a-b1ef406c9596','sinal.png','image/png','2016-09-29 13:26:18.024000','2016-09-29 13:26:18.024000'),
  ('cca473c2-47cd-4b08-b68a-75cd284d6f73','0f446e2a-9145-47c4-a5e6-7ba294e5e870','sinal.png','image/png','2016-09-29 13:25:59.286000','2016-09-29 13:25:59.286000'),
  ('dd68da1d-4078-40b9-bf43-a64ce80965ce','feca9e3d-eca4-4405-81d6-9e88dc1aa756','sinal.png','image/png','2016-09-29 13:25:56.823000','2016-09-29 13:25:56.823000'),
  ('e7e558ec-8cec-47ca-acc0-27234b751979','35fea56a-c53c-4e95-b063-21e22178578b','sinal.png','image/png','2016-09-29 13:26:15.546000','2016-09-29 13:26:15.546000'),
  ('f78953a2-72ed-4bbe-9475-6b6a17a7afc9','4c32f62e-7029-40f9-a94f-9e9d186dc2d5','sinal.png','image/png','2016-09-29 13:26:01.399000','2016-09-29 13:26:01.399000'),
  ('c52b3617-d233-40d6-8d07-b0284f6717ed','71911768-5744-4fd2-8e10-acbcbf61f4f7','croquigeral.jpg','image/jpeg', '2016-10-05 13:50:26.807','2016-10-05 13:50:26.807');

INSERT INTO `modelo_controladores` (`id`, `id_json`, `fabricante_id`, `descricao`, `limite_estagio`, `limite_grupo_semaforico`, `limite_anel`, `limite_detector_pedestre`, `limite_detector_veicular`, `limite_tabelas_entre_verdes`, `limite_planos`, `data_criacao`, `data_atualizacao`)
VALUES
  ('24c6f1e4-8661-11e6-9678-1f0de452edaf','24c6f1f8-8661-11e6-9678-1f0de452edaf','24bf38a0-8661-11e6-9678-1f0de452edaf','Modelo Básico',16,16,4,4,8,2,16,'2016-09-29 13:24:06.000000','2016-09-29 13:24:06.000000');

INSERT INTO `controladores` (`id`, `id_json`, `croqui_id`, `nome_endereco`, `sequencia`, `numero_smee`, `numero_smeeconjugado1`, `numero_smeeconjugado2`, `numero_smeeconjugado3`, `firmware`, `modelo_id`, `area_id`, `subarea_id`, `bloqueado`, `planos_bloqueado`, `data_criacao`, `data_atualizacao`)
VALUES
  ('21440a8c-764d-4605-a23e-ef3103c9f544',NULL, 'c52b3617-d233-40d6-8d07-b0284f6717ed', 'Av. Paulista com R. Bela Cintra',1,NULL,NULL,NULL,NULL,NULL,'24c6f1e4-8661-11e6-9678-1f0de452edaf','24bc8524-8661-11e6-9678-1f0de452edaf',NULL,0,0,'2016-09-29 13:25:30.457000','2016-09-29 13:30:42.648000');

INSERT INTO `aneis` (`id`, `id_json`, `ativo`, `descricao`, `posicao`, `numero_smee`, `aceita_modo_manual`, `controlador_id`, `croqui_id`, `data_criacao`, `data_atualizacao`)
VALUES
  ('287415c8-1d96-4ab1-a04f-377b19cd9238','5f5cfcb3-9d03-47f9-b0c4-86345d64760e',1,NULL,1,'-', '0','21440a8c-764d-4605-a23e-ef3103c9f544',NULL,'2016-09-29 13:25:30.463000','2016-09-29 13:30:42.650000'),
  ('4d3c3682-75d0-4944-8212-6f321718a951','20656ac9-27dd-4782-937b-28cb691dbdb3',0,NULL,3,NULL,'0','21440a8c-764d-4605-a23e-ef3103c9f544',NULL,'2016-09-29 13:25:30.465000','2016-09-29 13:30:42.733000'),
  ('dc415c07-60aa-4048-8c7d-0714745324af','9e816e30-b0d5-4608-a874-abd3ff8ca609',0,NULL,4,NULL,'0','21440a8c-764d-4605-a23e-ef3103c9f544',NULL,'2016-09-29 13:25:30.467000','2016-09-29 13:30:42.912000'),
  ('e4aea91d-790d-496f-b70e-887060734fd7','af5d0c03-c248-419d-b052-cbe20bc97b3b',1,NULL,2,NULL,'1','21440a8c-764d-4605-a23e-ef3103c9f544',NULL,'2016-09-29 13:25:30.464000','2016-09-29 13:30:42.713000');

INSERT INTO `enderecos` (`id`, `id_json`, `controlador_id`, `anel_id`, `localizacao`, `latitude`, `longitude`, `localizacao2`, `altura_numerica`, `referencia`, `data_criacao`, `data_atualizacao`)
VALUES
  ('17e77589-b5fd-4503-b40d-54c07ab2684e','0bca10bf-3e4c-4246-a073-a3a07eb5ccb0',NULL,'e4aea91d-790d-496f-b70e-887060734fd7','Alameda Campinas',-23.5681006,-46.65531850000002 ,'Av. Paulista',NULL,NULL,NOW(),NOW()),
  ('2c31b9e1-15b5-4181-a750-4a230865d552','76a14e10-9eaa-43d3-8c7a-6b877d969f80','21440a8c-764d-4605-a23e-ef3103c9f544',NULL,'Av. Paulista',-23.5631141,-46.65439200000003,'R. Bela Cintra',NULL,NULL,NOW(),NOW()),
  ('d2e14ef2-577d-4a1a-8ba9-fb94e74d4196','64c02bdc-e7b1-4b0d-8089-191819f5e47c',NULL,'287415c8-1d96-4ab1-a04f-377b19cd9238','Av. Paulista',-23.5631141,-46.65439200000003,'R. Bela Cintra',NULL,NULL,NOW(),NOW());

INSERT INTO `controladores_fisicos` (`id`, `id_json`, `area_id`, `data_criacao`, `data_atualizacao`)
VALUES
  ('fbac66f8-7d51-4a27-98e5-6a290aa48a45',NULL,'24bc8524-8661-11e6-9678-1f0de452edaf','2016-09-29 13:25:30.530000','2016-09-29 13:25:30.530000');

INSERT INTO `faixas_de_valores` (`id`, `id_json`, `tempo_defasagem_min`, `tempo_defasagem_max`, `tempo_amarelo_min`, `tempo_amarelo_max`, `tempo_vermelho_intermitente_min`, `tempo_vermelho_intermitente_max`, `tempo_vermelho_limpeza_veicular_min`, `tempo_vermelho_limpeza_veicular_max`,
                                 `tempo_vermelho_limpeza_pedestre_min`, `tempo_vermelho_limpeza_pedestre_max`, `tempo_atraso_grupo_min`, `tempo_atraso_grupo_max`, `tempo_verde_seguranca_veicular_min`, `tempo_verde_seguranca_veicular_max`, `tempo_verde_seguranca_pedestre_min`,
                                 `tempo_verde_seguranca_pedestre_max`, `tempo_maximo_permanencia_estagio_min`, `tempo_maximo_permanencia_estagio_max`, `default_tempo_maximo_permanencia_estagio_veicular`, `tempo_ciclo_min`, `tempo_ciclo_max`, `tempo_verde_minimo_min`, `tempo_verde_minimo_max`,
                                 `tempo_verde_maximo_min`, `tempo_verde_maximo_max`, `tempo_verde_intermediario_min`, `tempo_verde_intermediario_max`, `tempo_extensao_verde_min`, `tempo_extensao_verde_max`, `tempo_verde_min`, `tempo_verde_max`, `tempo_ausencia_deteccao_min`, `tempo_ausencia_deteccao_max`,
                                 `tempo_deteccao_permanente_min`, `tempo_deteccao_permanente_max`, `data_criacao`, `data_atualizacao`)
VALUES
  ('14e42bc7-849d-423b-866a-c7285233f0e1','47ef4a70-2341-460d-8193-40962fdcb89b',0,255,3,5,3,32,0,7,0,5,0,20,10,30,4,10,60,255,127,30,255,10,255,10,255,10,255,1,10,1,255,0,4320,0,1440,'2016-09-30 08:12:58.812000','2016-09-30 08:12:58.812000');

INSERT INTO `grupos_semaforicos` (`id`, `id_json`, `tipo`, `descricao`, `anel_id`, `controlador_id`, `posicao`, `fase_vermelha_apagada_amarelo_intermitente`, `tempo_verde_seguranca`, `data_criacao`, `data_atualizacao`)
VALUES
  ('227858c0-248f-4551-aeaf-6c3119fdb65f','96491646-57be-4099-bdc2-f05a33a0ebb0','VEICULAR',NULL,'e4aea91d-790d-496f-b70e-887060734fd7',NULL,5,1,10,'2016-09-29 13:26:56.140000','2016-09-29 13:30:42.718000'),
  ('4ac5742a-7357-4cb0-9801-0075132c9d9f','f9cb9125-5257-429e-b036-f71c81523e4d','VEICULAR',NULL,'287415c8-1d96-4ab1-a04f-377b19cd9238',NULL,1,1,10,'2016-09-29 13:26:56.111000','2016-09-29 13:30:42.658000'),
  ('a841dc7c-837e-46be-a46e-5ba8e1d27747','62c2c0bd-7ba6-407e-8b1d-2a5dc027bd53','PEDESTRE',NULL,'287415c8-1d96-4ab1-a04f-377b19cd9238',NULL,3,0,4,'2016-09-29 13:26:56.116000','2016-09-29 13:30:42.676000'),
  ('b2425ed0-dbd1-4591-bde7-82c6a18dfe3a','3ccf34e0-0f81-4968-b23b-956acd9e922a','VEICULAR',NULL,'287415c8-1d96-4ab1-a04f-377b19cd9238',NULL,2,1,10,'2016-09-29 13:26:56.113000','2016-09-29 13:30:42.693000'),
  ('d20ca39b-9e50-42b5-8e6a-6fddfb2ba7ef','50ad1a0d-7c5d-4366-a591-7816a6030579','VEICULAR',NULL,'e4aea91d-790d-496f-b70e-887060734fd7',NULL,4,1,10,'2016-09-29 13:26:56.137000','2016-09-29 13:30:42.725000');

INSERT INTO `estagios` (`id`, `id_json`, `imagem_id`, `descricao`, `tempo_maximo_permanencia`, `tempo_maximo_permanencia_ativado`, `posicao`, `demanda_prioritaria`, `tempo_verde_demanda_prioritaria`, `anel_id`, `controlador_id`, `data_criacao`, `data_atualizacao`)
VALUES
  ('108ed266-d7b4-461f-b4e9-e2117c3348a6','0683b056-76da-485a-afcb-aedf4a464808','9389d305-6bf4-40b6-b384-e2b75fb4195d',NULL,60,1,1,0,1,'e4aea91d-790d-496f-b70e-887060734fd7',NULL,'2016-09-29 13:26:35.709000','2016-09-29 13:30:42.732000'),
  ('12b2a949-6ab0-4467-b30a-be7c2ff92b05','2ee2d81b-ca68-4d96-902f-d8df6db22819','4caff4b3-c713-40f2-8840-ba342f7940bf',NULL,60,1,1,0,1,'287415c8-1d96-4ab1-a04f-377b19cd9238',NULL,'2016-09-29 13:26:35.697000','2016-09-29 13:30:42.709000'),
  ('28e7a329-ca3c-4431-916d-d2aa2283f7e6','1478a9e5-a5da-498b-b834-eca1c9aef8ad','e7e558ec-8cec-47ca-acc0-27234b751979',NULL,60,1,2,0,1,'e4aea91d-790d-496f-b70e-887060734fd7',NULL,'2016-09-29 13:26:35.708000','2016-09-29 13:30:42.732000'),
  ('63fc4b2b-85be-4900-a374-0e85fc7b1e85','d530fc77-bb7b-4fb2-8c97-9705b075c73b','f78953a2-72ed-4bbe-9475-6b6a17a7afc9',NULL,60,1,2,0,1,'287415c8-1d96-4ab1-a04f-377b19cd9238',NULL,'2016-09-29 13:26:35.702000','2016-09-29 13:30:42.710000'),
  ('a76a7b05-289b-460e-aad6-4901eb2cd1ba','efe5ead2-e9f2-4234-9021-d27a11875c29','cca473c2-47cd-4b08-b68a-75cd284d6f73',NULL,60,1,3,0,1,'287415c8-1d96-4ab1-a04f-377b19cd9238',NULL,'2016-09-29 13:26:35.700000','2016-09-29 13:30:42.711000'),
  ('e671e421-b7a0-4b56-97f6-9e48962a59e7','7e7adb6b-0aba-4975-b13f-df8bf183db70','dd68da1d-4078-40b9-bf43-a64ce80965ce',NULL,60,1,4,0,1,'287415c8-1d96-4ab1-a04f-377b19cd9238',NULL,'2016-09-29 13:26:35.698000','2016-09-29 13:30:42.712000');

INSERT INTO `estagios_grupos_semaforicos` (`id`, `id_json`, `estagio_id`, `grupo_semaforico_id`, `data_criacao`, `data_atualizacao`)
VALUES
  ('3b7f99c2-ecaf-4b28-9f51-e8d3da706781','8d5dcde4-8555-4c33-a148-d5ab5fa34835','108ed266-d7b4-461f-b4e9-e2117c3348a6','d20ca39b-9e50-42b5-8e6a-6fddfb2ba7ef','2016-09-29 13:27:29.878000','2016-09-29 13:30:42.726000'),
  ('669e3320-96cb-4bfb-9581-2ad09610831d','e9a46d12-ad01-4b68-926b-8c20ec7bdbed','e671e421-b7a0-4b56-97f6-9e48962a59e7','a841dc7c-837e-46be-a46e-5ba8e1d27747','2016-09-29 13:27:29.798000','2016-09-29 13:30:42.677000'),
  ('85fa127b-18e1-4792-983b-00c0a234b880','9206a5f9-e0c6-4dd9-8ffc-104cbe097475','28e7a329-ca3c-4431-916d-d2aa2283f7e6','227858c0-248f-4551-aeaf-6c3119fdb65f','2016-09-29 13:27:29.869000','2016-09-29 13:30:42.719000'),
  ('8de1192d-9085-46a6-87f0-8f9ceb14f2b4','18d379fa-466c-4072-bb0f-86acdd33bdc6','63fc4b2b-85be-4900-a374-0e85fc7b1e85','b2425ed0-dbd1-4591-bde7-82c6a18dfe3a','2016-09-29 13:27:29.843000','2016-09-29 13:30:42.694000'),
  ('8eb481e9-2558-4aa2-9326-a8402658efd7','2a26df7a-f342-4a64-b539-b1139594453b','12b2a949-6ab0-4467-b30a-be7c2ff92b05','4ac5742a-7357-4cb0-9801-0075132c9d9f','2016-09-29 13:27:29.778000','2016-09-29 13:30:42.659000'),
  ('abd58873-f6f7-4f0c-93fb-2ee6488203d1','7bd9d6ee-f8e1-4fd1-a444-a6efb77214cb','a76a7b05-289b-460e-aad6-4901eb2cd1ba','a841dc7c-837e-46be-a46e-5ba8e1d27747','2016-09-29 13:27:29.798000','2016-09-29 13:30:42.677000');

INSERT INTO `detectores` (`id`, `id_json`, `tipo`, `anel_id`, `estagio_id`, `controlador_id`, `posicao`, `descricao`, `monitorado`, `tempo_ausencia_deteccao`, `tempo_deteccao_permanente`, `data_criacao`, `data_atualizacao`)
VALUES
  ('2d133563-6914-48ab-b282-1349d78086a3','f5b30400-ebde-41cb-89a4-bc67e1d9cbc1','VEICULAR','287415c8-1d96-4ab1-a04f-377b19cd9238','12b2a949-6ab0-4467-b30a-be7c2ff92b05',NULL,1,NULL,1,0,0,'2016-09-29 13:30:42.654000','2016-09-29 13:30:42.654000'),
  ('34156874-3ad0-4157-8c76-4ec4d8e5cdaa','993363c2-a12d-4e02-bef8-9ca90fbf61f1','VEICULAR','e4aea91d-790d-496f-b70e-887060734fd7','28e7a329-ca3c-4431-916d-d2aa2283f7e6',NULL,4,NULL,1,0,0,'2016-09-29 13:30:42.716000','2016-09-29 13:30:42.716000'),
  ('41e5f77d-5434-49e3-b792-d3d883d0921c','0c9f408a-359a-457a-ba7d-4a8bc3928443','VEICULAR','e4aea91d-790d-496f-b70e-887060734fd7','108ed266-d7b4-461f-b4e9-e2117c3348a6',NULL,3,NULL,1,0,0,'2016-09-29 13:30:42.715000','2016-09-29 13:30:42.715000'),
  ('61ff6f35-2415-4bbe-bd28-c0fbada9c8bf','22f3e804-30e2-4481-8e6f-222b276ddff4','VEICULAR','287415c8-1d96-4ab1-a04f-377b19cd9238','63fc4b2b-85be-4900-a374-0e85fc7b1e85',NULL,2,NULL,1,0,0,'2016-09-29 13:30:42.656000','2016-09-29 13:30:42.656000'),
  ('9aecc1d9-f3b1-4e72-a45c-421c517aecf4','7ada8724-395a-48d3-9a78-544176d4ba7f','PEDESTRE','287415c8-1d96-4ab1-a04f-377b19cd9238','e671e421-b7a0-4b56-97f6-9e48962a59e7',NULL,2,NULL,1,0,0,'2016-09-29 13:30:42.652000','2016-09-29 13:30:42.652000'),
  ('c2db1c49-1ace-43ae-b056-be0bcf0db87d','9e80f5d6-dc19-44bd-b5b0-fb6d321ab81a','PEDESTRE','287415c8-1d96-4ab1-a04f-377b19cd9238','a76a7b05-289b-460e-aad6-4901eb2cd1ba',NULL,1,NULL,1,0,0,'2016-09-29 13:30:42.651000','2016-09-29 13:30:42.651000');


INSERT INTO `tabela_entre_verdes` (`id`, `id_json`, `descricao`, `grupo_semaforico_id`, `posicao`, `data_criacao`, `data_atualizacao`)
VALUES
  ('07a5805d-590d-480d-8b9f-c71dab55b3a9','6db8b60c-2088-45b2-8af6-4cf501ffb15a','PADRÃO','227858c0-248f-4551-aeaf-6c3119fdb65f',1,'2016-09-29 13:27:29.871000','2016-09-29 13:30:42.721000'),
  ('2bff6e45-418a-4b3e-8308-6c2a6cd91e03','0a10247e-42aa-4844-a450-8b27997edc76','PADRÃO','d20ca39b-9e50-42b5-8e6a-6fddfb2ba7ef',1,'2016-09-29 13:27:29.879000','2016-09-29 13:30:42.727000'),
  ('2cd4d64e-47f3-4f7f-9262-1d977c02d5b9','28bbcfc6-8620-49e3-b610-bfa930ec7267','PADRÃO','4ac5742a-7357-4cb0-9801-0075132c9d9f',1,'2016-09-29 13:27:29.779000','2016-09-29 13:30:42.661000'),
  ('6a333df2-1e3a-4f54-ab52-26b9798d8a60','01fa7986-2712-43be-a1d4-371d488d0f5e','PADRÃO','b2425ed0-dbd1-4591-bde7-82c6a18dfe3a',1,'2016-09-29 13:27:29.845000','2016-09-29 13:30:42.694000'),
  ('bcf1f549-8ff9-4849-9551-7f886c09aea4','d76ab366-4e33-4ad4-b5ef-c4019f1ad200','PADRÃO','a841dc7c-837e-46be-a46e-5ba8e1d27747',1,'2016-09-29 13:27:29.810000','2016-09-29 13:30:42.679000');

INSERT INTO `transicoes` (`id`, `id_json`, `tipo`, `grupo_semaforico_id`, `origem_id`, `destino_id`, `modo_intermitente_ou_apagado`, `destroy`, `data_criacao`, `data_atualizacao`)
VALUES
  ('146084eb-0009-4749-985f-25e3ab00e323', '7530f91e-798a-4397-9e1a-619ef337c3fa','PERDA_DE_PASSAGEM','4ac5742a-7357-4cb0-9801-0075132c9d9f', '12b2a949-6ab0-4467-b30a-be7c2ff92b05', 'e671e421-b7a0-4b56-97f6-9e48962a59e7','TRUE',  'FALSE', '2016-09-29 13:27:29.792', '2016-11-04 17:58:41.016'),
  ('1dc6a720-5599-4c1e-b96f-41db94a7af59', 'b45c70fd-8cf7-40dc-81f7-3c6c9a4eddad','PERDA_DE_PASSAGEM','4ac5742a-7357-4cb0-9801-0075132c9d9f', '12b2a949-6ab0-4467-b30a-be7c2ff92b05', '63fc4b2b-85be-4900-a374-0e85fc7b1e85','FALSE', 'FALSE', '2016-09-29 13:27:29.786', '2016-11-04 17:58:41.016'),
  ('abf51859-c7fb-4a0c-a642-86a8f811cf3d', '933a3108-e250-402b-94eb-2736db11d8ea','PERDA_DE_PASSAGEM','4ac5742a-7357-4cb0-9801-0075132c9d9f', '12b2a949-6ab0-4467-b30a-be7c2ff92b05', 'a76a7b05-289b-460e-aad6-4901eb2cd1ba','FALSE', 'FALSE', '2016-09-29 13:27:29.789', '2016-11-04 17:58:41.016'),
  ('0addf1b4-b03d-48b2-9b63-ddb5c55edbce', '942ac6c7-a5e3-4b95-ac48-d3c107089098','GANHO_DE_PASSAGEM','4ac5742a-7357-4cb0-9801-0075132c9d9f', 'a76a7b05-289b-460e-aad6-4901eb2cd1ba', '12b2a949-6ab0-4467-b30a-be7c2ff92b05','TRUE',  'FALSE', '2016-09-29 13:27:29.795', '2016-11-04 17:58:41.016'),
  ('83edd0ef-4de4-4d98-bbde-0aba58c9d687', 'c9d46f5f-f8a5-4d82-ae22-6802b66639a6','GANHO_DE_PASSAGEM','4ac5742a-7357-4cb0-9801-0075132c9d9f', 'e671e421-b7a0-4b56-97f6-9e48962a59e7', '12b2a949-6ab0-4467-b30a-be7c2ff92b05','TRUE',  'FALSE', '2016-09-29 13:27:29.796', '2016-11-04 17:58:41.016'),
  ('ea1fda24-3ec5-40b5-90e4-7c048441ec95', 'e2b105a2-1bf4-4c55-87c2-6bd32de42ca9','GANHO_DE_PASSAGEM','4ac5742a-7357-4cb0-9801-0075132c9d9f', '63fc4b2b-85be-4900-a374-0e85fc7b1e85', '12b2a949-6ab0-4467-b30a-be7c2ff92b05','TRUE',  'FALSE', '2016-09-29 13:27:29.794', '2016-11-04 17:58:41.016'),
  ('14b3696f-e30d-4453-99a5-027f075e1dc2', '87695e79-a423-492d-a785-3a2db391e1ab','PERDA_DE_PASSAGEM','b2425ed0-dbd1-4591-bde7-82c6a18dfe3a', '63fc4b2b-85be-4900-a374-0e85fc7b1e85', 'e671e421-b7a0-4b56-97f6-9e48962a59e7','TRUE',  'FALSE', '2016-09-29 13:27:29.855', '2016-11-04 17:58:41.017'),
  ('60a5085a-63d4-41ea-a680-f6624dae1f4d', 'f51c7c6a-c790-4ac2-9ed6-85b01f7bbe38','PERDA_DE_PASSAGEM','b2425ed0-dbd1-4591-bde7-82c6a18dfe3a', '63fc4b2b-85be-4900-a374-0e85fc7b1e85', 'a76a7b05-289b-460e-aad6-4901eb2cd1ba','FALSE', 'FALSE', '2016-09-29 13:27:29.853', '2016-11-04 17:58:41.017'),
  ('a2794583-953b-405f-be2d-f0f17e372b0d', '95a15afa-c4e3-4a5c-b274-ae265eb39692','PERDA_DE_PASSAGEM','b2425ed0-dbd1-4591-bde7-82c6a18dfe3a', '63fc4b2b-85be-4900-a374-0e85fc7b1e85', '12b2a949-6ab0-4467-b30a-be7c2ff92b05','FALSE', 'FALSE', '2016-09-29 13:27:29.851', '2016-11-04 17:58:41.017'),
  ('10387c82-258f-45fb-8a10-b0996f71d321', '4fbe206f-cf73-4870-a362-4b11705c4fd4','GANHO_DE_PASSAGEM','b2425ed0-dbd1-4591-bde7-82c6a18dfe3a', '12b2a949-6ab0-4467-b30a-be7c2ff92b05', '63fc4b2b-85be-4900-a374-0e85fc7b1e85','FALSE', 'FALSE', '2016-09-29 13:27:29.857', '2016-11-04 17:58:41.017'),
  ('33cd4c1f-eb6a-4bc4-881b-a1043dd0dd21', '084ee112-38b8-48d6-a3eb-06fb447ffe5e','GANHO_DE_PASSAGEM','b2425ed0-dbd1-4591-bde7-82c6a18dfe3a', 'e671e421-b7a0-4b56-97f6-9e48962a59e7', '63fc4b2b-85be-4900-a374-0e85fc7b1e85','FALSE', 'FALSE', '2016-09-29 13:27:29.859', '2016-11-04 17:58:41.018'),
  ('93f7c416-7682-4c61-b1d7-0b6251be2502', '2cb7a759-4116-4756-9abb-5795ab8b2560','GANHO_DE_PASSAGEM','b2425ed0-dbd1-4591-bde7-82c6a18dfe3a', 'a76a7b05-289b-460e-aad6-4901eb2cd1ba', '63fc4b2b-85be-4900-a374-0e85fc7b1e85','FALSE', 'FALSE', '2016-09-29 13:27:29.858', '2016-11-04 17:58:41.018'),
  ('2230aacd-2d32-4d6a-bdb7-cd0a85fbdafa', '1cdaf4ef-b833-4a32-be5d-24d150e9c1a4','PERDA_DE_PASSAGEM','a841dc7c-837e-46be-a46e-5ba8e1d27747', 'a76a7b05-289b-460e-aad6-4901eb2cd1ba', '63fc4b2b-85be-4900-a374-0e85fc7b1e85','TRUE',  'FALSE', '2016-09-29 13:27:29.819', '2016-11-04 17:58:41.018'),
  ('4b4f82d0-b307-43d4-a0c9-c8097acde1c8', 'f4060474-c88a-4f82-8cea-573e8379b508','PERDA_DE_PASSAGEM','a841dc7c-837e-46be-a46e-5ba8e1d27747', 'e671e421-b7a0-4b56-97f6-9e48962a59e7', '63fc4b2b-85be-4900-a374-0e85fc7b1e85','FALSE', 'FALSE', '2016-09-29 13:27:29.831', '2016-11-04 17:58:41.019'),
  ('66ed353f-a48b-4505-bc3f-de3f3f4f872a', '721f33e6-ced2-4c06-9a32-3493c419a26a','PERDA_DE_PASSAGEM','a841dc7c-837e-46be-a46e-5ba8e1d27747', 'e671e421-b7a0-4b56-97f6-9e48962a59e7', '12b2a949-6ab0-4467-b30a-be7c2ff92b05','TRUE',  'FALSE', '2016-09-29 13:27:29.829', '2016-11-04 17:58:41.019'),
  ('e6e735b9-bb8e-4c50-a5fe-6db429f87721', '5977ea75-03f9-4b49-a106-b14895bac838','PERDA_DE_PASSAGEM','a841dc7c-837e-46be-a46e-5ba8e1d27747', 'a76a7b05-289b-460e-aad6-4901eb2cd1ba', '12b2a949-6ab0-4467-b30a-be7c2ff92b05','FALSE', 'FALSE', '2016-09-29 13:27:29.817', '2016-11-04 17:58:41.019'),
  ('17d39d16-1c29-45e3-b93f-158849cc6740', 'c91be9df-4e0c-4658-ae0a-7f1a2baf71ff','GANHO_DE_PASSAGEM','a841dc7c-837e-46be-a46e-5ba8e1d27747', '12b2a949-6ab0-4467-b30a-be7c2ff92b05', 'e671e421-b7a0-4b56-97f6-9e48962a59e7','FALSE', 'FALSE', '2016-09-29 13:27:29.838', '2016-11-04 17:58:41.019'),
  ('87eb59cb-476d-44a3-a217-8e0ab1bc5e84', '4f1707c7-de39-4af8-9b8e-484b0c453494','GANHO_DE_PASSAGEM','a841dc7c-837e-46be-a46e-5ba8e1d27747', '63fc4b2b-85be-4900-a374-0e85fc7b1e85', 'e671e421-b7a0-4b56-97f6-9e48962a59e7','FALSE', 'FALSE', '2016-09-29 13:27:29.839', '2016-11-04 17:58:41.019'),
  ('d2e1b716-2b7b-4062-8aeb-820a7579ef50', '58f9128b-5953-419e-ac31-3b28ec4e5251','GANHO_DE_PASSAGEM','a841dc7c-837e-46be-a46e-5ba8e1d27747', '63fc4b2b-85be-4900-a374-0e85fc7b1e85', 'a76a7b05-289b-460e-aad6-4901eb2cd1ba','FALSE', 'FALSE', '2016-09-29 13:27:29.836', '2016-11-04 17:58:41.02'),
  ('fcae2d02-02e9-4ef7-af65-5360f8a506ff', 'e5d6acc6-25a2-44e6-9406-9cefdc6f0104','GANHO_DE_PASSAGEM','a841dc7c-837e-46be-a46e-5ba8e1d27747', '12b2a949-6ab0-4467-b30a-be7c2ff92b05', 'a76a7b05-289b-460e-aad6-4901eb2cd1ba','FALSE', 'FALSE', '2016-09-29 13:27:29.835', '2016-11-04 17:58:41.02'),
  ('3b0815b7-4e21-4a0e-b132-068e20e301d9', '485004a3-646d-4d25-8206-76ad0cdcecd6','PERDA_DE_PASSAGEM','d20ca39b-9e50-42b5-8e6a-6fddfb2ba7ef', '108ed266-d7b4-461f-b4e9-e2117c3348a6', '28e7a329-ca3c-4431-916d-d2aa2283f7e6','TRUE',  'FALSE', '2016-09-29 13:27:29.881', '2016-11-04 17:58:41.104'),
  ('3b922bb7-d0ec-45f9-83ad-d7e20e947d6b', '5c0def15-5a44-4557-9b72-880eeadce165','GANHO_DE_PASSAGEM','d20ca39b-9e50-42b5-8e6a-6fddfb2ba7ef', '28e7a329-ca3c-4431-916d-d2aa2283f7e6', '108ed266-d7b4-461f-b4e9-e2117c3348a6','FALSE', 'FALSE', '2016-09-29 13:27:29.883', '2016-11-04 17:58:41.104'),
  ('9382305f-d51f-43c2-bc15-2c1c1aa32d60', '684fc0f1-6518-4f4f-93e1-5269448aabb5','PERDA_DE_PASSAGEM','227858c0-248f-4551-aeaf-6c3119fdb65f', '28e7a329-ca3c-4431-916d-d2aa2283f7e6', '108ed266-d7b4-461f-b4e9-e2117c3348a6','TRUE',  'FALSE', '2016-09-29 13:27:29.872', '2016-11-04 17:58:41.104'),
  ('d66f7a07-91ce-4030-ac10-507860979e47', '91662774-0b6b-4d26-b426-81872bb2c35c','GANHO_DE_PASSAGEM','227858c0-248f-4551-aeaf-6c3119fdb65f', '108ed266-d7b4-461f-b4e9-e2117c3348a6', '28e7a329-ca3c-4431-916d-d2aa2283f7e6','TRUE',  'FALSE', '2016-09-29 13:27:29.875', '2016-11-04 17:58:41.105');



INSERT INTO `tabela_entre_verdes_transicao` (`id`, `id_json`, `tabela_entre_verdes_id`, `transicao_id`, `tempo_amarelo`, `tempo_vermelho_intermitente`, `tempo_vermelho_limpeza`, `data_criacao`, `data_atualizacao`)
VALUES
  ('1c95b148-8aa7-47d6-b214-13d3ce41916f','1a63ba52-474e-4728-a7b7-5fd4b52624ca','2bff6e45-418a-4b3e-8308-6c2a6cd91e03','3b0815b7-4e21-4a0e-b132-068e20e301d9',3,NULL,0,'2016-09-29 13:27:29.879000','2016-09-29 13:30:42.728000'),
  ('50abc659-67a9-4d53-b942-940bbb864894','2637e88c-4f77-4536-a47f-1d710ba08ff7','2cd4d64e-47f3-4f7f-9262-1d977c02d5b9','abf51859-c7fb-4a0c-a642-86a8f811cf3d',3,NULL,0,'2016-09-29 13:27:29.782000','2016-09-29 13:30:42.663000'),
  ('513c03fc-49ae-4c6f-8a40-0f9b006f0a92','9b2d8e8c-bd76-4595-adba-d10bfb677bb2','07a5805d-590d-480d-8b9f-c71dab55b3a9','9382305f-d51f-43c2-bc15-2c1c1aa32d60',3,NULL,0,'2016-09-29 13:27:29.871000','2016-09-29 13:30:42.722000'),
  ('6f3d6067-7a37-40a3-b1dd-e0887848f800','4dd65ebc-db74-45d1-a0aa-d35da7ae54ac','bcf1f549-8ff9-4849-9551-7f886c09aea4','66ed353f-a48b-4505-bc3f-de3f3f4f872a',NULL,3,0,'2016-09-29 13:27:29.814000','2016-09-29 13:30:42.680000'),
  ('6f668058-83cd-46df-b50c-cf4a3cbda1d9','32ddcb36-eaa3-466c-8ef2-8f2fabcd018b','2cd4d64e-47f3-4f7f-9262-1d977c02d5b9','1dc6a720-5599-4c1e-b96f-41db94a7af59',3,NULL,0,'2016-09-29 13:27:29.780000','2016-09-29 13:30:42.663000'),
  ('79225c97-6bca-4ae0-9ab3-6b52bde71829','ed5a7698-c5f7-47b1-837b-e3cb0a95c91d','bcf1f549-8ff9-4849-9551-7f886c09aea4','e6e735b9-bb8e-4c50-a5fe-6db429f87721',NULL,3,0,'2016-09-29 13:27:29.811000','2016-09-29 13:30:42.681000'),
  ('8646c751-4dc5-409c-85ad-f44fbcafba31','df1dc463-7e85-461a-9503-aa49b62dbe4a','2cd4d64e-47f3-4f7f-9262-1d977c02d5b9','146084eb-0009-4749-985f-25e3ab00e323',3,NULL,0,'2016-09-29 13:27:29.783000','2016-09-29 13:30:42.664000'),
  ('99f01cff-b9f3-4b1a-ba4e-7d5a2e879fae','3a043c27-1356-4ec8-b73f-c63c952c58fc','6a333df2-1e3a-4f54-ab52-26b9798d8a60','14b3696f-e30d-4453-99a5-027f075e1dc2',3,NULL,0,'2016-09-29 13:27:29.850000','2016-09-29 13:30:42.695000'),
  ('b04ec85e-3716-4995-b5bd-ccbf9f1c1a0f','4455e3c8-68ed-4430-8460-065df37406c4','bcf1f549-8ff9-4849-9551-7f886c09aea4','4b4f82d0-b307-43d4-a0c9-c8097acde1c8',NULL,3,0,'2016-09-29 13:27:29.815000','2016-09-29 13:30:42.681000'),
  ('b2a22d91-2d40-4d72-bee8-f6598ab97b9a','f4e36870-4daa-4ee2-a9a2-c0983a2085b6','6a333df2-1e3a-4f54-ab52-26b9798d8a60','a2794583-953b-405f-be2d-f0f17e372b0d',3,NULL,0,'2016-09-29 13:27:29.847000','2016-09-29 13:30:42.696000'),
  ('b849a3cd-fcc6-44a4-8dbd-c5f06190d3a5','f4a79dc6-9ab7-47b1-bfdc-364c7d8984c4','6a333df2-1e3a-4f54-ab52-26b9798d8a60','60a5085a-63d4-41ea-a680-f6624dae1f4d',3,NULL,0,'2016-09-29 13:27:29.848000','2016-09-29 13:30:42.697000'),
  ('bf0ce30f-1269-4691-93b0-257174d65215','4fcb2d58-6621-4458-92f9-302815ed4710','bcf1f549-8ff9-4849-9551-7f886c09aea4','2230aacd-2d32-4d6a-bdb7-cd0a85fbdafa',NULL,3,0,'2016-09-29 13:27:29.812000','2016-09-29 13:30:42.682000');

INSERT INTO `atrasos_de_grupos` (`id`, `id_json`, `transicao_id`, `atraso_de_grupo`, `data_criacao`, `data_atualizacao`)
VALUES
  ('0135bf01-04cd-42f9-b824-47a6beb48933','a3096ab0-c374-445a-a2dc-849b9be29371','33cd4c1f-eb6a-4bc4-881b-a1043dd0dd21',0,'2016-09-29 13:28:06.167000','2016-09-29 13:30:42.708000'),
  ('0497cf00-fa9f-4d88-a4b1-b9a54e925cff','4195338d-21da-4268-8d8c-136a7c72c510','0addf1b4-b03d-48b2-9b63-ddb5c55edbce',0,'2016-09-29 13:28:06.151000','2016-09-29 13:30:42.673000'),
  ('066153e2-2ef2-4519-af2e-b45260cdefa5','69b83bf3-e6fd-4e8e-88f7-085d171e2a70','93f7c416-7682-4c61-b1d7-0b6251be2502',0,'2016-09-29 13:28:06.169000','2016-09-29 13:30:42.709000'),
  ('077a0cc1-cbbc-4d21-b43c-efe9a14f392a','fa061285-f721-4d64-9b00-5b1b1a54498d','3b922bb7-d0ec-45f9-83ad-d7e20e947d6b',0,'2016-09-29 13:28:06.202000','2016-09-29 13:30:42.731000'),
  ('1890ee4e-5673-4256-9233-feaaea914246','9f173b45-600f-4e4b-9beb-893297cf90ea','fcae2d02-02e9-4ef7-af65-5360f8a506ff',0,'2016-09-29 13:28:06.188000','2016-09-29 13:30:42.693000'),
  ('22bf2147-359f-4331-9bfe-6e87e57880c1','c6601584-157d-4cd9-8811-ad74ba6f3657','4b4f82d0-b307-43d4-a0c9-c8097acde1c8',0,'2016-09-29 13:28:06.176000','2016-09-29 13:30:42.685000'),
  ('2bc47174-4f83-4eba-8632-7a100bacb1e6','146f35a3-1dbd-48ce-a118-0dc524d9eeb4','146084eb-0009-4749-985f-25e3ab00e323',0,'2016-09-29 13:28:06.145000','2016-09-29 13:30:42.665000'),
  ('2ed0ebe3-c0a0-4bc0-b932-80454554b127','ce97422b-130a-4e24-a494-0cc10db0ab12','a2794583-953b-405f-be2d-f0f17e372b0d',0,'2016-09-29 13:28:06.164000','2016-09-29 13:30:42.706000'),
  ('52de246e-c2d7-4408-9958-a9851be95ac3','3a447453-ef60-4dbf-a469-06041d3e6392','60a5085a-63d4-41ea-a680-f6624dae1f4d',0,'2016-09-29 13:28:06.163000','2016-09-29 13:30:42.700000'),
  ('55cf5678-739e-4a1f-8cfb-6ccde330ab88','b75f1246-b480-4158-bf51-2101db28be3b','10387c82-258f-45fb-8a10-b0996f71d321',0,'2016-09-29 13:28:06.166000','2016-09-29 13:30:42.707000'),
  ('6c206018-d526-43f2-a3c6-4e8f4c5d20f8','f869bcf3-f830-4130-b754-d22c039c1920','abf51859-c7fb-4a0c-a642-86a8f811cf3d',0,'2016-09-29 13:28:06.149000','2016-09-29 13:30:42.670000'),
  ('729af2e5-2b4f-4ae7-a585-ecf8727c1765','d0559340-ccff-4c4b-80de-3ae1c54d58aa','66ed353f-a48b-4505-bc3f-de3f3f4f872a',0,'2016-09-29 13:28:06.178000','2016-09-29 13:30:42.686000'),
  ('750b242e-0bda-4278-94db-733f8acbd66f','67eb1c7a-e983-4e89-b9d0-20c322d5a438','87eb59cb-476d-44a3-a217-8e0ab1bc5e84',0,'2016-09-29 13:28:06.185000','2016-09-29 13:30:42.691000'),
  ('7c1624b3-9472-45d2-93c4-8343914bf9a9','e7ea37b1-9666-46aa-8df2-0e3854f32f45','2230aacd-2d32-4d6a-bdb7-cd0a85fbdafa',0,'2016-09-29 13:28:06.175000','2016-09-29 13:30:42.683000'),
  ('93cfa4e5-b9f9-4c01-83e7-3901fb095f85','58dd0eea-ff23-44f4-92a9-75f7a1ed0e88','17d39d16-1c29-45e3-b93f-158849cc6740',0,'2016-09-29 13:28:06.183000','2016-09-29 13:30:42.690000'),
  ('9d2fe840-813f-4609-97ce-5382125cb58f','d0995657-eda3-45d0-beda-83d553e07f2b','ea1fda24-3ec5-40b5-90e4-7c048441ec95',0,'2016-09-29 13:28:06.155000','2016-09-29 13:30:42.675000'),
  ('a5f2f314-c1a6-4764-b861-cdab8c9e3fba','37763ef1-0b16-4fdd-b42e-14f4945eeebe','1dc6a720-5599-4c1e-b96f-41db94a7af59',0,'2016-09-29 13:28:06.146000','2016-09-29 13:30:42.668000'),
  ('ad1c4afd-a8ff-43c6-9a63-18c56dc4c402','4e22fb8e-4bb9-4d09-ae6f-191673a8bd55','d66f7a07-91ce-4030-ac10-507860979e47',0,'2016-09-29 13:28:06.209000','2016-09-29 13:30:42.725000'),
  ('d1a757c7-2266-4cee-b0b9-80cb99555655','97af4bcc-eab6-4c7e-b784-57632bc69d4b','83edd0ef-4de4-4d98-bbde-0aba58c9d687',0,'2016-09-29 13:28:06.153000','2016-09-29 13:30:42.674000'),
  ('db0b5617-2111-429c-b4f7-9fc4743cfa93','c3cc26c7-01ae-4aa6-a0ff-5b7a6ac08099','9382305f-d51f-43c2-bc15-2c1c1aa32d60',0,'2016-09-29 13:28:06.207000','2016-09-29 13:30:42.723000'),
  ('dd476df0-c9f4-4ed3-8080-8b654444c741','3ced1511-5b04-4385-8353-e5787ea17790','e6e735b9-bb8e-4c50-a5fe-6db429f87721',0,'2016-09-29 13:28:06.180000','2016-09-29 13:30:42.688000'),
  ('f57474e3-7108-4d59-80da-c20fbbc6b3b4','6d1c697e-c77c-4f29-9760-7295941643ec','14b3696f-e30d-4453-99a5-027f075e1dc2',0,'2016-09-29 13:28:06.161000','2016-09-29 13:30:42.698000'),
  ('f6bacf10-68ae-4609-b84b-b2c0caef1e5d','78701e9e-f476-4b54-9138-f4d5094f063a','d2e1b716-2b7b-4062-8aeb-820a7579ef50',0,'2016-09-29 13:28:06.186000','2016-09-29 13:30:42.692000'),
  ('fcd4ae5e-6664-427a-8fe5-43e87056c0d1','8d518911-d80a-41b0-ac22-652631e9b8d4','3b0815b7-4e21-4a0e-b132-068e20e301d9',0,'2016-09-29 13:28:06.201000','2016-09-29 13:30:42.730000');

INSERT INTO `transicoes_proibidas` (`id`, `id_json`, `origem_id`, `destino_id`, `alternativo_id`, `data_criacao`, `data_atualizacao`)
VALUES
  ('89d39143-f11f-45a8-816e-1f8f9bfe8e42','caa48cae-32bd-4363-bae0-11bac60de0cf','a76a7b05-289b-460e-aad6-4901eb2cd1ba','e671e421-b7a0-4b56-97f6-9e48962a59e7','a76a7b05-289b-460e-aad6-4901eb2cd1ba','2016-09-29 13:27:47.908000','2016-09-29 13:30:42.711000'),
  ('9f5876f5-460c-426c-8162-498e9ea99c80','64a151e7-4cb3-4928-80b6-1b5cadb036dc','e671e421-b7a0-4b56-97f6-9e48962a59e7','a76a7b05-289b-460e-aad6-4901eb2cd1ba','12b2a949-6ab0-4467-b30a-be7c2ff92b05','2016-09-29 13:27:47.912000','2016-09-29 13:30:42.713000');

INSERT INTO `verdes_conflitantes` (`id`, `id_json`, `origem_id`, `destino_id`, `data_criacao`, `data_atualizacao`)
VALUES
  ('14106451-6a4f-4075-b244-a4f3c6a4b9df','8af31c9a-bcc1-46c7-9ed5-355a242712be','d20ca39b-9e50-42b5-8e6a-6fddfb2ba7ef','227858c0-248f-4551-aeaf-6c3119fdb65f','2016-09-29 13:27:03.357000','2016-09-29 13:30:42.720000'),
  ('730249be-b9f5-4099-81dc-d8b53c0f2878','d2c5f905-ca94-427f-8585-5ccb33afcbee','b2425ed0-dbd1-4591-bde7-82c6a18dfe3a','a841dc7c-837e-46be-a46e-5ba8e1d27747','2016-09-29 13:27:03.351000','2016-09-29 13:30:42.678000'),
  ('75f43180-ad3f-4b41-b9fa-048d993e4bc1','11160cd4-9745-4b23-8639-38249692697c','4ac5742a-7357-4cb0-9801-0075132c9d9f','b2425ed0-dbd1-4591-bde7-82c6a18dfe3a','2016-09-29 13:27:03.349000','2016-09-29 13:30:42.660000');

INSERT INTO `versoes_controladores` (`id`, `id_json`, `controlador_origem_id`, `controlador_id`, `controlador_fisico_id`, `usuario_id`, `descricao`, `status_versao`, `data_criacao`)
VALUES
  ('45a311d6-f7c1-4231-a851-13851641121b',NULL,NULL,'21440a8c-764d-4605-a23e-ef3103c9f544','fbac66f8-7d51-4a27-98e5-6a290aa48a45','90574f0a-cd7b-477c-9fa6-98c426813ea8','Controlador criado pelo usuário: Administrador Geral','EDITANDO','2016-09-29 13:25:30.531000');
