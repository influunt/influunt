DELETE FROM `versoes_controladores`;
DELETE FROM `controladores_fisicos`;
DELETE FROM `controladores`;
DELETE FROM `modelo_controladores`;
DELETE FROM `fabricantes`;
DELETE FROM `imagens`;

SET @UsurioId = (SELECT id FROM "usuarios" where login = 'mobilab');
SET @Area1Id = (SELECT id FROM areas where descricao = '1');

SET @CidadeId  = RANDOM_UUID();
SET @Area2Id   = RANDOM_UUID();
SET @FabricanteId = RANDOM_UUID();
SET @ModeloId = RANDOM_UUID();
SET @Controlador1Id = RANDOM_UUID();
SET @Controlador2Id = RANDOM_UUID();
SET @ControladorFisico1Id = RANDOM_UUID();
SET @ControladorFisico2Id = RANDOM_UUID();
SET @VersaoControlador1Id = RANDOM_UUID();
SET @VersaoControlador2Id = RANDOM_UUID();
SET @Croqui1Id = RANDOM_UUID();
SET @Croqui2Id = RANDOM_UUID();

INSERT INTO `cidades` (`id`, `id_json`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@CidadeId, RANDOM_UUID(),'São Paulo',NOW(),NOW());
INSERT INTO `areas` (`id`, `id_json`, `descricao`, `cidade_id`, `data_criacao`, `data_atualizacao`) VALUES
  (@Area2Id, RANDOM_UUID(),2,@CidadeId,NOW(),NOW());

INSERT INTO `controladores_fisicos` (`id`, `id_json`, `area_id`, `data_criacao`, `data_atualizacao`)
VALUES
  (@ControladorFisico1Id,NULL,@Area1Id,NOW(),NOW()),
  (@ControladorFisico2Id,NULL,@Area2Id,NOW(),NOW());

INSERT INTO `fabricantes` (`id`, `id_json`, `nome`, `data_criacao`, `data_atualizacao`) VALUES (@FabricanteId,RANDOM_UUID(),'Raro Labs',NOW(),NOW());
INSERT INTO `modelo_controladores` (`id`, `id_json`, `fabricante_id`, `descricao`, `limite_estagio`, `limite_grupo_semaforico`, `limite_anel`, `limite_detector_pedestre`, `limite_detector_veicular`, `limite_tabelas_entre_verdes`, `limite_planos`, `data_criacao`, `data_atualizacao`)
VALUES
  (@ModeloId,RANDOM_UUID(),@FabricanteId,'Modelo Básico',16,16,4,4,8,2,16,NOW(),NOW());

INSERT INTO `imagens` (`id`, `id_json`, `filename`, `content_type`, `data_criacao`, `data_atualizacao`) VALUES
  (@Croqui1Id,RANDOM_UUID(),'sinal.png','image/png',NOW(),NOW()),
  (@Croqui2Id,RANDOM_UUID(),'sinal.png','image/png',NOW(),NOW());

INSERT INTO `controladores` (`id`, `id_json`, `croqui_id`, `nome_endereco`, `sequencia`, `numero_smee`, `numero_smeeconjugado1`, `numero_smeeconjugado2`, `numero_smeeconjugado3`, `firmware`, `modelo_id`, `area_id`, `subarea_id`,`sincronizado`,`exclusivo_para_teste`, `data_criacao`, `data_atualizacao`)
VALUES
  (@Controlador1Id,RANDOM_UUID(), @Croqui1Id, 'Av. Paulista com R. Bela Cintra',1,NULL,NULL,NULL,NULL,NULL, @ModeloId, @Area1Id,NULL,0,0, NOW(),NOW()),
  (@Controlador2Id,RANDOM_UUID(), @Croqui2Id, 'R. Bela Cintra com Av. Paulista',1,NULL,NULL,NULL,NULL,NULL, @ModeloId, @Area2Id,NULL,0,0, NOW(),NOW());

INSERT INTO `enderecos` (`id`, `id_json`, `controlador_id`, `anel_id`, `localizacao`, `latitude`, `longitude`, `localizacao2`, `altura_numerica`, `referencia`, `data_criacao`, `data_atualizacao`)
VALUES
  (RANDOM_UUID(),RANDOM_UUID(),@Controlador1Id,NULL,'Av. Amazonas',-19.9,-43.9720783,'Av. Contorno',NULL,NULL,NOW(),NOW()),
  (RANDOM_UUID(),RANDOM_UUID(),@Controlador2Id,NULL,'Av. Paulista',-23.5,-46.6543921,'R. Bela Cintra',NULL,NULL,NOW(),NOW());

INSERT INTO `versoes_controladores` (`id`, `id_json`, `controlador_origem_id`, `controlador_id`, `controlador_fisico_id`, `usuario_id`, `descricao`, `status_versao`, `data_criacao`, `data_atualizacao`)
VALUES
  (@VersaoControlador1Id,NULL,NULL,@Controlador1Id,@ControladorFisico1Id,@UsurioId,'Controlador criado pelo usuário: Administrador Geral','EDITANDO',NOW(),NOW()),
  (@VersaoControlador2Id,NULL,NULL,@Controlador2Id,@ControladorFisico2Id,@UsurioId,'Controlador criado pelo usuário: Administrador Geral','EDITANDO',NOW(),NOW());
