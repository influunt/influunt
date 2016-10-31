DELETE FROM `versoes_controladores`;
DELETE FROM `controladores_fisicos`;
DELETE FROM `controladores`;
DELETE FROM `modelo_controladores`;
DELETE FROM `fabricantes`;
DELETE FROM `imagens`;

SET @UsurioId = (SELECT id FROM "usuarios" where login = 'mobilab');

SET @CidadeId  = RANDOM_UUID();
SET @Area1Id   = RANDOM_UUID();
SET @Area2Id   = RANDOM_UUID();
SET @Area3Id   = RANDOM_UUID();
SET @Area4Id   = RANDOM_UUID();
SET @FabricanteId = RANDOM_UUID();
SET @ModeloId = RANDOM_UUID();
SET @Controlador1Id = RANDOM_UUID();
SET @Controlador2Id = RANDOM_UUID();
SET @Controlador3Id = RANDOM_UUID();
SET @Controlador4Id = RANDOM_UUID();
SET @ControladorFisico1Id = RANDOM_UUID();
SET @ControladorFisico2Id = RANDOM_UUID();
SET @ControladorFisico3Id = RANDOM_UUID();
SET @ControladorFisico4Id = RANDOM_UUID();
SET @VersaoControlador1Id = RANDOM_UUID();
SET @VersaoControlador2Id = RANDOM_UUID();
SET @VersaoControlador3Id = RANDOM_UUID();
SET @VersaoControlador4Id = RANDOM_UUID();
SET @Croqui1Id = RANDOM_UUID();
SET @Croqui2Id = RANDOM_UUID();
SET @Croqui3Id = RANDOM_UUID();
SET @Croqui4Id = RANDOM_UUID();

INSERT INTO `cidades` (`id`, `id_json`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@CidadeId, RANDOM_UUID(),'São Paulo',NOW(),NOW());
INSERT INTO `areas` (`id`, `id_json`, `descricao`, `cidade_id`, `data_criacao`, `data_atualizacao`) VALUES
  (@Area1Id, RANDOM_UUID(),1,@CidadeId,NOW(),NOW()),
  (@Area2Id, RANDOM_UUID(),2,@CidadeId,NOW(),NOW()),
  (@Area3Id, RANDOM_UUID(),3,@CidadeId,NOW(),NOW()),
  (@Area4Id, RANDOM_UUID(),4,@CidadeId,NOW(),NOW());

INSERT INTO `controladores_fisicos` (`id`, `id_json`, `area_id`, `data_criacao`, `data_atualizacao`)
VALUES
  (@ControladorFisico1Id,NULL,@Area1Id,NOW(),NOW()),
  (@ControladorFisico2Id,NULL,@Area2Id,NOW(),NOW()),
  (@ControladorFisico3Id,NULL,@Area3Id,NOW(),NOW()),
  (@ControladorFisico4Id,NULL,@Area4Id,NOW(),NOW());

INSERT INTO `fabricantes` (`id`, `id_json`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@FabricanteId,RANDOM_UUID(),'Raro Labs',NOW(),NOW());
INSERT INTO `modelo_controladores` (`id`, `id_json`, `fabricante_id`, `descricao`, `limite_estagio`, `limite_grupo_semaforico`, `limite_anel`, `limite_detector_pedestre`, `limite_detector_veicular`, `limite_tabelas_entre_verdes`, `limite_planos`, `data_criacao`, `data_atualizacao`)
VALUES
  (@ModeloId,RANDOM_UUID(),@FabricanteId,'Modelo Básico',16,16,4,4,8,2,16,NOW(),NOW());

INSERT INTO `imagens` (`id`, `id_json`, `filename`, `content_type`, `data_criacao`, `data_atualizacao`) VALUES
  (@Croqui1Id,RANDOM_UUID(),'sinal.png','image/png',NOW(),NOW()),
  (@Croqui2Id,RANDOM_UUID(),'sinal.png','image/png',NOW(),NOW()),
  (@Croqui3Id,RANDOM_UUID(),'sinal.png','image/png',NOW(),NOW()),
  (@Croqui4Id,RANDOM_UUID(),'sinal.png','image/png',NOW(),NOW());

INSERT INTO `controladores` (`id`, `id_json`, `croqui_id`, `nome_endereco`, `sequencia`, `numero_smee`, `numero_smeeconjugado1`, `numero_smeeconjugado2`, `numero_smeeconjugado3`, `firmware`, `modelo_id`, `area_id`, `subarea_id`, `data_criacao`, `data_atualizacao`)
VALUES
  (@Controlador1Id,RANDOM_UUID(), @Croqui1Id, 'Av. Paulista com R. Bela Cintra',1,NULL,NULL,NULL,NULL,NULL, @ModeloId, @Area1Id,NULL, NOW(),NOW()),
  (@Controlador2Id,RANDOM_UUID(), @Croqui2Id, 'R. Bela Cintra com Av. Paulista',1,NULL,NULL,NULL,NULL,NULL, @ModeloId, @Area2Id,NULL, NOW(),NOW()),
  (@Controlador3Id,RANDOM_UUID(), @Croqui3Id, 'Av. Paulista, nº 2000',1,NULL,NULL,NULL,NULL,NULL, @ModeloId, @Area3Id,NULL, NOW(),NOW()),
  (@Controlador4Id,RANDOM_UUID(), @Croqui4Id, 'Av. Paulista com R. Pamplona',1,NULL,NULL,NULL,NULL,NULL, @ModeloId, @Area4Id,NULL, NOW(),NOW());

INSERT INTO `enderecos` (`id`, `id_json`, `controlador_id`, `anel_id`, `localizacao`, `latitude`, `longitude`, `localizacao2`, `altura_numerica`, `referencia`, `data_criacao`, `data_atualizacao`)
VALUES
  (RANDOM_UUID(),RANDOM_UUID(),@Controlador1Id,NULL,'Av. Amazonas',-19.90,-43.9720783,'Av. Contorno',NULL,NULL,NOW(),NOW()),
  (RANDOM_UUID(),RANDOM_UUID(),@Controlador2Id,NULL,'Av. Paulista',-23.56,-46.6543921,'R. Bela Cintra',NULL,NULL,NOW(),NOW()),
  (RANDOM_UUID(),RANDOM_UUID(),@Controlador3Id,NULL,'Av. Paulista',-23.56,-46.6543921,NULL,'2000',NULL,NOW(),NOW()),
  (RANDOM_UUID(),RANDOM_UUID(),@Controlador4Id,NULL,'Av. Paulista',-23.56,-46.6543921,'R. Pamplona',NULL,NULL,NOW(),NOW());

INSERT INTO `versoes_controladores` (`id`, `id_json`, `controlador_origem_id`, `controlador_id`, `controlador_fisico_id`, `usuario_id`, `descricao`, `status_versao`, `data_criacao`)
VALUES
  (@VersaoControlador1Id,NULL,NULL,@Controlador1Id,@ControladorFisico1Id,@UsurioId,'Controlador criado pelo usuário: Administrador Geral','EDITANDO',NOW()),
  (@VersaoControlador2Id,NULL,NULL,@Controlador2Id,@ControladorFisico2Id,@UsurioId,'Controlador criado pelo usuário: Administrador Geral','EDITANDO',NOW()),
  (@VersaoControlador3Id,NULL,NULL,@Controlador3Id,@ControladorFisico3Id,@UsurioId,'Controlador criado pelo usuário: Administrador Geral','CONFIGURADO',NOW()),
  (@VersaoControlador4Id,NULL,NULL,@Controlador4Id,@ControladorFisico4Id,@UsurioId,'Controlador criado pelo usuário: Administrador Geral','EDITANDO',NOW());
