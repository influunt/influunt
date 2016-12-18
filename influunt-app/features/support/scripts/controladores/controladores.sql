SET @UsuarioId = (SELECT id FROM "usuarios" where login = 'mobilab');

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
SET @Croqui5Id = RANDOM_UUID();
SET @Croqui6Id = RANDOM_UUID();
SET @Croqui7Id = RANDOM_UUID();
SET @Croqui8Id = RANDOM_UUID();
SET @Croqui9Id = RANDOM_UUID();
SET @Croqui10Id = RANDOM_UUID();
SET @Croqui11Id = RANDOM_UUID();
SET @Croqui12Id = RANDOM_UUID();
SET @Croqui13Id = RANDOM_UUID();
SET @Croqui14Id = RANDOM_UUID();
SET @Croqui15Id = RANDOM_UUID();
SET @Croqui16Id = RANDOM_UUID();
SET @Croqui17Id = RANDOM_UUID();
SET @Croqui18Id = RANDOM_UUID();
SET @Croqui19Id = RANDOM_UUID();
SET @Croqui20Id = RANDOM_UUID();
SET @Croqui21Id = RANDOM_UUID();
SET @Croqui22Id = RANDOM_UUID();
SET @Croqui23Id = RANDOM_UUID();
SET @Croqui24Id = RANDOM_UUID();

SET @AnelId1  = RANDOM_UUID();
SET @AnelId2  = RANDOM_UUID();
SET @AnelId3  = RANDOM_UUID();
SET @AnelId4  = RANDOM_UUID();
SET @AnelId5  = RANDOM_UUID();
SET @AnelId6  = RANDOM_UUID();
SET @AnelId7  = RANDOM_UUID();
SET @AnelId8  = RANDOM_UUID();
SET @AnelId9  = RANDOM_UUID();
SET @AnelId10 = RANDOM_UUID();
SET @AnelId11 = RANDOM_UUID();
SET @AnelId12 = RANDOM_UUID();
SET @AnelId13 = RANDOM_UUID();
SET @AnelId14 = RANDOM_UUID();
SET @AnelId15 = RANDOM_UUID();
SET @AnelId16 = RANDOM_UUID();

SET @EnderecoId1 = RANDOM_UUID();
SET @EnderecoId2 = RANDOM_UUID();
SET @EnderecoId3 = RANDOM_UUID();
SET @EnderecoId4 = RANDOM_UUID();
SET @EnderecoId5 = RANDOM_UUID();
SET @EnderecoId6 = RANDOM_UUID();
SET @EnderecoId7 = RANDOM_UUID();
SET @EnderecoId8 = RANDOM_UUID();

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
  (@Croqui4Id,RANDOM_UUID(),'sinal.png','image/png',NOW(),NOW()),
  (@Croqui5Id,RANDOM_UUID(),'sinal.png','image/png',NOW(),NOW()),
  (@Croqui6Id,RANDOM_UUID(),'sinal.png','image/png',NOW(),NOW()),
  (@Croqui7Id,RANDOM_UUID(),'sinal.png','image/png',NOW(),NOW()),
  (@Croqui8Id,RANDOM_UUID(),'sinal.png','image/png',NOW(),NOW()),
  (@Croqui9Id,RANDOM_UUID(),'sinal.png','image/png',NOW(),NOW()),
  (@Croqui10Id,RANDOM_UUID(),'sinal.png','image/png',NOW(),NOW()),
  (@Croqui11Id,RANDOM_UUID(),'sinal.png','image/png',NOW(),NOW()),
  (@Croqui12Id,RANDOM_UUID(),'sinal.png','image/png',NOW(),NOW()),
  (@Croqui13Id,RANDOM_UUID(),'sinal.png','image/png',NOW(),NOW()),
  (@Croqui14Id,RANDOM_UUID(),'sinal.png','image/png',NOW(),NOW()),
  (@Croqui15Id,RANDOM_UUID(),'sinal.png','image/png',NOW(),NOW()),
  (@Croqui16Id,RANDOM_UUID(),'sinal.png','image/png',NOW(),NOW()),
  (@Croqui17Id,RANDOM_UUID(),'sinal.png','image/png',NOW(),NOW()),
  (@Croqui18Id,RANDOM_UUID(),'sinal.png','image/png',NOW(),NOW()),
  (@Croqui19Id,RANDOM_UUID(),'sinal.png','image/png',NOW(),NOW()),
  (@Croqui20Id,RANDOM_UUID(),'sinal.png','image/png',NOW(),NOW()),
  (@Croqui21Id,RANDOM_UUID(),'sinal.png','image/png',NOW(),NOW()),
  (@Croqui22Id,RANDOM_UUID(),'sinal.png','image/png',NOW(),NOW()),
  (@Croqui23Id,RANDOM_UUID(),'sinal.png','image/png',NOW(),NOW()),
  (@Croqui24Id,RANDOM_UUID(),'sinal.png','image/png',NOW(),NOW());

INSERT INTO `controladores` (`id`, `id_json`, `croqui_id`, `nome_endereco`, `sequencia`, `numero_smee`, `numero_smeeconjugado1`, `numero_smeeconjugado2`, `numero_smeeconjugado3`, `firmware`, `modelo_id`, `area_id`, `subarea_id`, `bloqueado`, `planos_bloqueado`,`sincronizado`,`exclusivo_para_teste`,`data_criacao`, `data_atualizacao`)
VALUES
  (@Controlador1Id,RANDOM_UUID(), NULL, 'Av. Paulista com R. Bela Cintra',1,NULL,NULL,NULL,NULL,NULL, @ModeloId, @Area1Id,NULL,'FALSE', 'FALSE', 'FALSE', 'FALSE', NOW(),NOW()),
  (@Controlador2Id,RANDOM_UUID(), NULL, 'R. Bela Cintra com Av. Paulista',1,NULL,NULL,NULL,NULL,NULL, @ModeloId, @Area2Id,NULL,'FALSE', 'FALSE','FALSE', 'FALSE', NOW(),NOW()),
  (@Controlador3Id,RANDOM_UUID(), NULL, 'Av. Paulista, nº 2000',1,NULL,NULL,NULL,NULL,NULL, @ModeloId, @Area3Id,NULL, 'FALSE', 'FALSE','FALSE', 'FALSE', NOW(),NOW()),
  (@Controlador4Id,RANDOM_UUID(), NULL, 'Av. Paulista com R. Pamplona',1,NULL,NULL,NULL,NULL,NULL, @ModeloId, @Area4Id,NULL,'FALSE', 'FALSE', 'FALSE', 'FALSE', NOW(),NOW());

INSERT INTO `enderecos` (`id`, `id_json`, `controlador_id`, `anel_id`, `localizacao`, `latitude`, `longitude`, `localizacao2`, `altura_numerica`, `referencia`, `data_criacao`, `data_atualizacao`)
VALUES
  (RANDOM_UUID(),RANDOM_UUID(),@Controlador1Id,NULL,'Av. Amazonas',-19.90,-43.9720783,'Av. Contorno',NULL,NULL,NOW(),NOW()),
  (RANDOM_UUID(),RANDOM_UUID(),@Controlador2Id,NULL,'Av. Paulista',-23.56,-46.6543921,'R. Bela Cintra',NULL,NULL,NOW(),NOW()),
  (RANDOM_UUID(),RANDOM_UUID(),@Controlador3Id,NULL,'Av. Paulista',-23.56,-46.6543921,NULL,'2000',NULL,NOW(),NOW()),
  (RANDOM_UUID(),RANDOM_UUID(),@Controlador4Id,NULL,'Av. Paulista',-23.56,-46.6543921,'R. Pamplona',NULL,NULL,NOW(),NOW());

INSERT INTO `versoes_controladores` (`id`, `id_json`, `controlador_origem_id`, `controlador_id`, `controlador_fisico_id`, `usuario_id`, `descricao`, `status_versao`, `data_criacao`, `data_atualizacao`)
VALUES
  (@VersaoControlador1Id,NULL,NULL,@Controlador1Id,@ControladorFisico1Id,@UsuarioId,'Controlador criado pelo usuário: mobilab','EDITANDO',NOW(),NOW()),
  (@VersaoControlador2Id,NULL,NULL,@Controlador2Id,@ControladorFisico2Id,@UsuarioId,'Controlador criado pelo usuário: mobilab','EDITANDO',NOW(),NOW()),
  (@VersaoControlador3Id,NULL,NULL,@Controlador3Id,@ControladorFisico3Id,@UsuarioId,'Controlador criado pelo usuário: mobilab','CONFIGURADO',NOW(),NOW()),
  (@VersaoControlador4Id,NULL,NULL,@Controlador4Id,@ControladorFisico4Id,@UsuarioId,'Controlador criado pelo usuário: mobilab','EDITANDO',NOW(),NOW());

INSERT INTO `aneis` (`id`, `id_json`, `ativo`, `descricao`, `posicao`, `numero_smee`, `aceita_modo_manual`, `controlador_id`, `croqui_id`, `data_criacao`, `data_atualizacao`)
VALUES
  (@AnelId1,RANDOM_UUID(),1,NULL,1,'-', '0',@Controlador1Id,NULL,NOW(),NOW()),
  (@AnelId2,RANDOM_UUID(),0,NULL,3,NULL,'0',@Controlador1Id,NULL,NOW(),NOW()),
  (@AnelId3,RANDOM_UUID(),0,NULL,4,NULL,'0',@Controlador1Id,NULL,NOW(),NOW()),
  (@AnelId4,RANDOM_UUID(),1,NULL,2,NULL,'1',@Controlador1Id,NULL,NOW(),NOW()),
  (@AnelId5,RANDOM_UUID(),1,NULL,1,'-', '0',@Controlador2Id,NULL,NOW(),NOW()),
  (@AnelId6,RANDOM_UUID(),0,NULL,3,NULL,'0',@Controlador2Id,NULL,NOW(),NOW()),
  (@AnelId7,RANDOM_UUID(),0,NULL,4,NULL,'0',@Controlador2Id,NULL,NOW(),NOW()),
  (@AnelId8,RANDOM_UUID(),1,NULL,2,NULL,'1',@Controlador2Id,NULL,NOW(),NOW()),
  (@AnelId9,RANDOM_UUID(),1,NULL,1,'-', '0',@Controlador3Id,NULL,NOW(),NOW()),
  (@AnelId10,RANDOM_UUID(),0,NULL,3,NULL,'0',@Controlador3Id,NULL,NOW(),NOW()),
  (@AnelId11,RANDOM_UUID(),0,NULL,4,NULL,'0',@Controlador3Id,NULL,NOW(),NOW()),
  (@AnelId12,RANDOM_UUID(),1,NULL,2,NULL,'1',@Controlador3Id,NULL,NOW(),NOW()),
  (@AnelId13,RANDOM_UUID(),1,NULL,1,'-', '0',@Controlador4Id,NULL,NOW(),NOW()),
  (@AnelId14,RANDOM_UUID(),0,NULL,3,NULL,'0',@Controlador4Id,NULL,NOW(),NOW()),
  (@AnelId15,RANDOM_UUID(),0,NULL,4,NULL,'0',@Controlador4Id,NULL,NOW(),NOW()),
  (@AnelId16,RANDOM_UUID(),1,NULL,2,NULL,'1',@Controlador4Id,NULL,NOW(),NOW());

INSERT INTO `enderecos` (`id`, `id_json`, `controlador_id`, `anel_id`, `localizacao`, `latitude`, `longitude`, `localizacao2`, `altura_numerica`, `referencia`, `data_criacao`, `data_atualizacao`)
VALUES
  (@EnderecoId1,RANDOM_UUID(),NULL,@AnelId1,'Alameda Campinas',-23.5681006,-46.65531850000002 ,'Av. Paulista',NULL,NULL,NOW(),NOW()),
  (@EnderecoId2,RANDOM_UUID(),NULL,@AnelId4,'Av. Paulista',-23.5631141,-46.65439200000003,'R. Bela Cintra',NULL,NULL,NOW(),NOW()),
  (@EnderecoId3,RANDOM_UUID(),NULL,@AnelId5,'Alameda Campinas',-23.5681006,-46.65531850000002 ,'Av. Paulista',NULL,NULL,NOW(),NOW()),
  (@EnderecoId4,RANDOM_UUID(),NULL,@AnelId8,'Av. Paulista',-23.5631141,-46.65439200000003,'R. Bela Cintra',NULL,NULL,NOW(),NOW()),
  (@EnderecoId5,RANDOM_UUID(),NULL,@AnelId9,'Alameda Campinas',-23.5681006,-46.65531850000002 ,'Av. Paulista',NULL,NULL,NOW(),NOW()),
  (@EnderecoId6,RANDOM_UUID(),NULL,@AnelId12,'Av. Paulista',-23.5631141,-46.65439200000003,'R. Bela Cintra',NULL,NULL,NOW(),NOW()),
  (@EnderecoId7,RANDOM_UUID(),NULL,@AnelId13,'Alameda Campinas',-23.5681006,-46.65531850000002 ,'Av. Paulista',NULL,NULL,NOW(),NOW()),
  (@EnderecoId8,RANDOM_UUID(),NULL,@AnelId16,'Av. Paulista',-23.5631141,-46.65439200000003,'R. Bela Cintra',NULL,NULL,NOW(),NOW());

SET @FaixaValorerId  = RANDOM_UUID();
INSERT INTO `faixas_de_valores` (`id`, `id_json`, `tempo_defasagem_min`, `tempo_defasagem_max`, `tempo_amarelo_min`, `tempo_amarelo_max`, `tempo_vermelho_intermitente_min`, `tempo_vermelho_intermitente_max`, `tempo_vermelho_limpeza_veicular_min`, `tempo_vermelho_limpeza_veicular_max`,
                                 `tempo_vermelho_limpeza_pedestre_min`, `tempo_vermelho_limpeza_pedestre_max`, `tempo_atraso_grupo_min`, `tempo_atraso_grupo_max`, `tempo_verde_seguranca_veicular_min`, `tempo_verde_seguranca_veicular_max`, `tempo_verde_seguranca_pedestre_min`,
                                 `tempo_verde_seguranca_pedestre_max`, `tempo_maximo_permanencia_estagio_min`, `tempo_maximo_permanencia_estagio_max`, `default_tempo_maximo_permanencia_estagio_veicular`, `tempo_ciclo_min`, `tempo_ciclo_max`, `tempo_verde_minimo_min`, `tempo_verde_minimo_max`,
                                 `tempo_verde_maximo_min`, `tempo_verde_maximo_max`, `tempo_verde_intermediario_min`, `tempo_verde_intermediario_max`, `tempo_extensao_verde_min`, `tempo_extensao_verde_max`, `tempo_verde_min`, `tempo_verde_max`, `tempo_ausencia_deteccao_min`, `tempo_ausencia_deteccao_max`,
                                 `tempo_deteccao_permanente_min`, `tempo_deteccao_permanente_max`, `data_criacao`, `data_atualizacao`)
VALUES
  (@FaixaValorerId,RANDOM_UUID(),0,255,3,5,3,32,0,7,0,5,0,20,10,30,4,10,60,255,127,30,255,10,255,10,255,10,255,1,10,1,255,0,4320,0,1440,'2016-09-30 08:12:58.812000','2016-09-30 08:12:58.812000');

SET @GrupoSemaforicoId1  = RANDOM_UUID();
SET @GrupoSemaforicoId2  = RANDOM_UUID();
SET @GrupoSemaforicoId3  = RANDOM_UUID();
SET @GrupoSemaforicoId4  = RANDOM_UUID();
SET @GrupoSemaforicoId5  = RANDOM_UUID();
SET @GrupoSemaforicoId6  = RANDOM_UUID();
SET @GrupoSemaforicoId7  = RANDOM_UUID();
SET @GrupoSemaforicoId8  = RANDOM_UUID();
SET @GrupoSemaforicoId9  = RANDOM_UUID();
SET @GrupoSemaforicoId10 = RANDOM_UUID();
SET @GrupoSemaforicoId11 = RANDOM_UUID();
SET @GrupoSemaforicoId12 = RANDOM_UUID();
SET @GrupoSemaforicoId13 = RANDOM_UUID();
SET @GrupoSemaforicoId14 = RANDOM_UUID();
SET @GrupoSemaforicoId15 = RANDOM_UUID();
SET @GrupoSemaforicoId16 = RANDOM_UUID();
SET @GrupoSemaforicoId17 = RANDOM_UUID();
SET @GrupoSemaforicoId18 = RANDOM_UUID();
SET @GrupoSemaforicoId19 = RANDOM_UUID();
SET @GrupoSemaforicoId20 = RANDOM_UUID();

INSERT INTO `grupos_semaforicos` (`id`, `id_json`, `tipo`, `descricao`, `anel_id`, `controlador_id`, `posicao`, `fase_vermelha_apagada_amarelo_intermitente`, `tempo_verde_seguranca`, `data_criacao`, `data_atualizacao`)
VALUES
  (@GrupoSemaforicoId1,RANDOM_UUID(),'VEICULAR',NULL,@AnelId4,NULL,5,1,10,NOW(),NOW()),
  (@GrupoSemaforicoId2,RANDOM_UUID(),'VEICULAR',NULL,@AnelId1,NULL,1,1,10, NOW(),NOW()),
  (@GrupoSemaforicoId3,RANDOM_UUID(),'PEDESTRE',NULL,@AnelId1,NULL,3,0,4,  NOW(),NOW()),
  (@GrupoSemaforicoId4,RANDOM_UUID(),'VEICULAR',NULL,@AnelId1,NULL,2,1,10, NOW(),NOW()),
  (@GrupoSemaforicoId5,RANDOM_UUID(),'VEICULAR',NULL,@AnelId4,NULL,4,1,10,NOW(),NOW()),

  (@GrupoSemaforicoId6,RANDOM_UUID(),'VEICULAR',NULL,@AnelId8,NULL,5,1,10,NOW(),NOW()),
  (@GrupoSemaforicoId7,RANDOM_UUID(),'VEICULAR',NULL,@AnelId5,NULL,1,1,10, NOW(),NOW()),
  (@GrupoSemaforicoId8,RANDOM_UUID(),'PEDESTRE',NULL,@AnelId5,NULL,3,0,4,  NOW(),NOW()),
  (@GrupoSemaforicoId9,RANDOM_UUID(),'VEICULAR',NULL,@AnelId5,NULL,2,1,10, NOW(),NOW()),
  (@GrupoSemaforicoId10,RANDOM_UUID(),'VEICULAR',NULL,@AnelId8,NULL,4,1,10,NOW(),NOW()),

  (@GrupoSemaforicoId11,RANDOM_UUID(),'VEICULAR',NULL,@AnelId12,NULL,5,1,10,NOW(),NOW()),
  (@GrupoSemaforicoId12,RANDOM_UUID(),'VEICULAR',NULL,@AnelId9,NULL,1,1,10, NOW(),NOW()),
  (@GrupoSemaforicoId13,RANDOM_UUID(),'PEDESTRE',NULL,@AnelId9,NULL,3,0,4,  NOW(),NOW()),
  (@GrupoSemaforicoId14,RANDOM_UUID(),'VEICULAR',NULL,@AnelId9,NULL,2,1,10, NOW(),NOW()),
  (@GrupoSemaforicoId15,RANDOM_UUID(),'VEICULAR',NULL,@AnelId12,NULL,4,1,10,NOW(),NOW()),

  (@GrupoSemaforicoId16,RANDOM_UUID(),'VEICULAR',NULL,@AnelId16,NULL,5,1,10,NOW(),NOW()),
  (@GrupoSemaforicoId17,RANDOM_UUID(),'VEICULAR',NULL,@AnelId13,NULL,1,1,10, NOW(),NOW()),
  (@GrupoSemaforicoId18,RANDOM_UUID(),'PEDESTRE',NULL,@AnelId13,NULL,3,0,4,  NOW(),NOW()),
  (@GrupoSemaforicoId19,RANDOM_UUID(),'VEICULAR',NULL,@AnelId13,NULL,2,1,10, NOW(),NOW()),
  (@GrupoSemaforicoId20,RANDOM_UUID(),'VEICULAR',NULL,@AnelId16,NULL,4,1,10,NOW(),NOW());


SET @EstagioId1  = RANDOM_UUID();
SET @EstagioId2  = RANDOM_UUID();
SET @EstagioId3  = RANDOM_UUID();
SET @EstagioId4  = RANDOM_UUID();
SET @EstagioId5  = RANDOM_UUID();
SET @EstagioId6  = RANDOM_UUID();
SET @EstagioId7  = RANDOM_UUID();
SET @EstagioId8  = RANDOM_UUID();
SET @EstagioId9  = RANDOM_UUID();
SET @EstagioId10 = RANDOM_UUID();
SET @EstagioId11 = RANDOM_UUID();
SET @EstagioId12 = RANDOM_UUID();
SET @EstagioId13 = RANDOM_UUID();
SET @EstagioId14 = RANDOM_UUID();
SET @EstagioId15 = RANDOM_UUID();
SET @EstagioId16 = RANDOM_UUID();
SET @EstagioId17 = RANDOM_UUID();
SET @EstagioId18 = RANDOM_UUID();
SET @EstagioId19 = RANDOM_UUID();
SET @EstagioId20 = RANDOM_UUID();
SET @EstagioId21 = RANDOM_UUID();
SET @EstagioId22 = RANDOM_UUID();
SET @EstagioId23 = RANDOM_UUID();
SET @EstagioId24 = RANDOM_UUID();

INSERT INTO `estagios` (`id`, `id_json`, `imagem_id`, `descricao`, `tempo_maximo_permanencia`, `tempo_maximo_permanencia_ativado`, `posicao`, `demanda_prioritaria`, `tempo_verde_demanda_prioritaria`, `anel_id`, `controlador_id`, `data_criacao`, `data_atualizacao`)
VALUES
  (@EstagioId1,RANDOM_UUID(), @Croqui1Id,NULL,60,1,1,0,1,@AnelId4,NULL, NOW(),NOW()),
  (@EstagioId2,RANDOM_UUID(), @Croqui2Id,NULL,60,1,1,0,1,@AnelId1,NULL, NOW(),NOW()),
  (@EstagioId3,RANDOM_UUID(), @Croqui3Id,NULL,60,1,2,0,1,@AnelId4,NULL, NOW(),NOW()),
  (@EstagioId4,RANDOM_UUID(), @Croqui4Id,NULL,60,1,2,0,1,@AnelId1,NULL, NOW(),NOW()),
  (@EstagioId5,RANDOM_UUID(), @Croqui5Id,NULL,60,1,3,0,1,@AnelId1,NULL, NOW(),NOW()),
  (@EstagioId6,RANDOM_UUID(), @Croqui6Id,NULL,60,1,4,0,1,@AnelId1,NULL, NOW(),NOW()),

  (@EstagioId7,RANDOM_UUID(), @Croqui7Id,NULL,60,1,1,0,1,@AnelId8,NULL, NOW(),NOW()),
  (@EstagioId8,RANDOM_UUID(), @Croqui8Id,NULL,60,1,1,0,1,@AnelId5,NULL, NOW(),NOW()),
  (@EstagioId9,RANDOM_UUID(), @Croqui9Id,NULL,60,1,2,0,1,@AnelId8,NULL, NOW(),NOW()),
  (@EstagioId10,RANDOM_UUID(),@Croqui10Id,NULL,60,1,2,0,1,@AnelId5,NULL, NOW(),NOW()),
  (@EstagioId11,RANDOM_UUID(),@Croqui11Id,NULL,60,1,3,0,1,@AnelId5,NULL, NOW(),NOW()),
  (@EstagioId12,RANDOM_UUID(),@Croqui12Id,NULL,60,1,4,0,1,@AnelId5,NULL, NOW(),NOW()),

  (@EstagioId13,RANDOM_UUID(),@Croqui13Id,NULL,60,1,1,0,1,@AnelId12,NULL,NOW(),NOW()),
  (@EstagioId14,RANDOM_UUID(),@Croqui14Id,NULL,60,1,1,0,1,@AnelId9,NULL, NOW(),NOW()),
  (@EstagioId15,RANDOM_UUID(),@Croqui15Id,NULL,60,1,2,0,1,@AnelId12,NULL,NOW(),NOW()),
  (@EstagioId16,RANDOM_UUID(),@Croqui16Id,NULL,60,1,2,0,1,@AnelId9,NULL, NOW(),NOW()),
  (@EstagioId17,RANDOM_UUID(),@Croqui17Id,NULL,60,1,3,0,1,@AnelId9,NULL, NOW(),NOW()),
  (@EstagioId18,RANDOM_UUID(),@Croqui18Id,NULL,60,1,4,0,1,@AnelId9,NULL, NOW(),NOW()),

  (@EstagioId19,RANDOM_UUID(),@Croqui19Id,NULL,60,1,1,0,1,@AnelId16,NULL,NOW(),NOW()),
  (@EstagioId20,RANDOM_UUID(),@Croqui20Id,NULL,60,1,1,0,1,@AnelId13,NULL,NOW(),NOW()),
  (@EstagioId21,RANDOM_UUID(),@Croqui21Id,NULL,60,1,2,0,1,@AnelId16,NULL,NOW(),NOW()),
  (@EstagioId22,RANDOM_UUID(),@Croqui22Id,NULL,60,1,2,0,1,@AnelId13,NULL,NOW(),NOW()),
  (@EstagioId23,RANDOM_UUID(),@Croqui23Id,NULL,60,1,3,0,1,@AnelId13,NULL,NOW(),NOW()),
  (@EstagioId24,RANDOM_UUID(),@Croqui24Id,NULL,60,1,4,0,1,@AnelId13,NULL,NOW(),NOW());

SET @EstGruSemaf1  = RANDOM_UUID();
SET @EstGruSemaf2  = RANDOM_UUID();
SET @EstGruSemaf3  = RANDOM_UUID();
SET @EstGruSemaf4  = RANDOM_UUID();
SET @EstGruSemaf5  = RANDOM_UUID();
SET @EstGruSemaf6  = RANDOM_UUID();
SET @EstGruSemaf7  = RANDOM_UUID();
SET @EstGruSemaf8  = RANDOM_UUID();
SET @EstGruSemaf9  = RANDOM_UUID();
SET @EstGruSemaf10 = RANDOM_UUID();
SET @EstGruSemaf11 = RANDOM_UUID();
SET @EstGruSemaf12 = RANDOM_UUID();
SET @EstGruSemaf13 = RANDOM_UUID();
SET @EstGruSemaf14 = RANDOM_UUID();
SET @EstGruSemaf15 = RANDOM_UUID();
SET @EstGruSemaf16 = RANDOM_UUID();
SET @EstGruSemaf17 = RANDOM_UUID();
SET @EstGruSemaf18 = RANDOM_UUID();
SET @EstGruSemaf19 = RANDOM_UUID();
SET @EstGruSemaf20 = RANDOM_UUID();
SET @EstGruSemaf21 = RANDOM_UUID();
SET @EstGruSemaf22 = RANDOM_UUID();
SET @EstGruSemaf23 = RANDOM_UUID();
SET @EstGruSemaf24 = RANDOM_UUID();

INSERT INTO `estagios_grupos_semaforicos` (`id`, `id_json`, `estagio_id`, `grupo_semaforico_id`, `data_criacao`, `data_atualizacao`)
VALUES
  (@EstGruSemaf1,RANDOM_UUID(),@EstagioId1,@GrupoSemaforicoId1,NOW(),NOW()),
  (@EstGruSemaf2,RANDOM_UUID(),@EstagioId2,@GrupoSemaforicoId2,NOW(),NOW()),
  (@EstGruSemaf3,RANDOM_UUID(),@EstagioId3,@GrupoSemaforicoId3,NOW(),NOW()),
  (@EstGruSemaf4,RANDOM_UUID(),@EstagioId4,@GrupoSemaforicoId4,NOW(),NOW()),
  (@EstGruSemaf5,RANDOM_UUID(),@EstagioId5,@GrupoSemaforicoId5,NOW(),NOW()),
  (@EstGruSemaf6,RANDOM_UUID(),@EstagioId6,@GrupoSemaforicoId1,NOW(),NOW()),

  (@EstGruSemaf7,RANDOM_UUID(),@EstagioId7,@GrupoSemaforicoId6,NOW(),NOW()),
  (@EstGruSemaf8,RANDOM_UUID(),@EstagioId8,@GrupoSemaforicoId7,NOW(),NOW()),
  (@EstGruSemaf9,RANDOM_UUID(),@EstagioId9,@GrupoSemaforicoId8,NOW(),NOW()),
  (@EstGruSemaf10,RANDOM_UUID(),@EstagioId10,@GrupoSemaforicoId9,NOW(),NOW()),
  (@EstGruSemaf11,RANDOM_UUID(),@EstagioId11,@GrupoSemaforicoId10,NOW(),NOW()),
  (@EstGruSemaf12,RANDOM_UUID(),@EstagioId12,@GrupoSemaforicoId6,NOW(),NOW()),

  (@EstGruSemaf13,RANDOM_UUID(),@EstagioId13,@GrupoSemaforicoId11,NOW(),NOW()),
  (@EstGruSemaf14,RANDOM_UUID(),@EstagioId14,@GrupoSemaforicoId12,NOW(),NOW()),
  (@EstGruSemaf15,RANDOM_UUID(),@EstagioId15,@GrupoSemaforicoId13,NOW(),NOW()),
  (@EstGruSemaf16,RANDOM_UUID(),@EstagioId16,@GrupoSemaforicoId14,NOW(),NOW()),
  (@EstGruSemaf17,RANDOM_UUID(),@EstagioId17,@GrupoSemaforicoId15,NOW(),NOW()),
  (@EstGruSemaf18,RANDOM_UUID(),@EstagioId18,@GrupoSemaforicoId11,NOW(),NOW()),

  (@EstGruSemaf19,RANDOM_UUID(),@EstagioId19,@GrupoSemaforicoId16,NOW(),NOW()),
  (@EstGruSemaf20,RANDOM_UUID(),@EstagioId20,@GrupoSemaforicoId17,NOW(),NOW()),
  (@EstGruSemaf21,RANDOM_UUID(),@EstagioId21,@GrupoSemaforicoId18,NOW(),NOW()),
  (@EstGruSemaf22,RANDOM_UUID(),@EstagioId22,@GrupoSemaforicoId19,NOW(),NOW()),
  (@EstGruSemaf23,RANDOM_UUID(),@EstagioId23,@GrupoSemaforicoId20,NOW(),NOW()),
  (@EstGruSemaf24,RANDOM_UUID(),@EstagioId24,@GrupoSemaforicoId16,NOW(),NOW());

SET @DetectorID1  = RANDOM_UUID();
SET @DetectorID2  = RANDOM_UUID();
SET @DetectorID3  = RANDOM_UUID();
SET @DetectorID4  = RANDOM_UUID();
SET @DetectorID5  = RANDOM_UUID();
SET @DetectorID6  = RANDOM_UUID();
SET @DetectorID7  = RANDOM_UUID();
SET @DetectorID8  = RANDOM_UUID();
SET @DetectorID9  = RANDOM_UUID();
SET @DetectorID10 = RANDOM_UUID();
SET @DetectorID11 = RANDOM_UUID();
SET @DetectorID12 = RANDOM_UUID();

INSERT INTO `detectores` (`id`, `id_json`, `tipo`, `anel_id`, `estagio_id`, `posicao`, `descricao`, `monitorado`, `tempo_ausencia_deteccao`, `tempo_deteccao_permanente`, `data_criacao`, `data_atualizacao`)
VALUES
  (@DetectorID1,RANDOM_UUID(),'VEICULAR',@AnelId1,@EstagioId2,1,NULL,1,0,0,NOW(),NOW()),
  (@DetectorID2,RANDOM_UUID(),'VEICULAR',@AnelId4,@EstagioId3,4,NULL,1,0,0,NOW(),NOW()),
  (@DetectorID3,RANDOM_UUID(),'VEICULAR',@AnelId4,@EstagioId1,3,NULL,1,0,0,NOW(),NOW()),
  (@DetectorID4,RANDOM_UUID(),'VEICULAR',@AnelId1,@EstagioId4,2,NULL,1,0,0,NOW(),NOW()),
  (@DetectorID5,RANDOM_UUID(),'PEDESTRE',@AnelId1,@EstagioId6,2,NULL,1,0,0,NOW(),NOW()),
  (@DetectorID6,RANDOM_UUID(),'PEDESTRE',@AnelId1,@EstagioId5,1,NULL,1,0,0,NOW(),NOW()),

  (@DetectorID7,RANDOM_UUID(),'VEICULAR',@AnelId5,@EstagioId8,1,NULL,1,0,0,NOW(),NOW()),
  (@DetectorID8,RANDOM_UUID(),'VEICULAR',@AnelId8,@EstagioId9,4,NULL,1,0,0,NOW(),NOW()),
  (@DetectorID9,RANDOM_UUID(),'VEICULAR',@AnelId8,@EstagioId7,3,NULL,1,0,0,NOW(),NOW()),
  (@DetectorID10,RANDOM_UUID(),'VEICULAR',@AnelId5,@EstagioId10,2,NULL,1,0,0,NOW(),NOW()),
  (@DetectorID11,RANDOM_UUID(),'PEDESTRE',@AnelId5,@EstagioId12,2,NULL,1,0,0,NOW(),NOW()),
  (@DetectorID12,RANDOM_UUID(),'PEDESTRE',@AnelId5,@EstagioId11,1,NULL,1,0,0,NOW(),NOW());


SET @TabelaEntreVerdesId1  = RANDOM_UUID();
SET @TabelaEntreVerdesId2  = RANDOM_UUID();
SET @TabelaEntreVerdesId3  = RANDOM_UUID();
SET @TabelaEntreVerdesId4  = RANDOM_UUID();
SET @TabelaEntreVerdesId5  = RANDOM_UUID();
SET @TabelaEntreVerdesId6  = RANDOM_UUID();
SET @TabelaEntreVerdesId7  = RANDOM_UUID();
SET @TabelaEntreVerdesId8  = RANDOM_UUID();
SET @TabelaEntreVerdesId9  = RANDOM_UUID();
SET @TabelaEntreVerdesId10 = RANDOM_UUID();

INSERT INTO `tabela_entre_verdes` (`id`, `id_json`, `descricao`, `grupo_semaforico_id`, `posicao`, `data_criacao`, `data_atualizacao`)
VALUES
  (@TabelaEntreVerdesId1 ,RANDOM_UUID(),'PADRÃO',@GrupoSemaforicoId1,1,NOW(),NOW()),
  (@TabelaEntreVerdesId2 ,RANDOM_UUID(),'PADRÃO',@GrupoSemaforicoId5,1,NOW(),NOW()),
  (@TabelaEntreVerdesId3 ,RANDOM_UUID(),'PADRÃO',@GrupoSemaforicoId2,1,NOW(),NOW()),
  (@TabelaEntreVerdesId4 ,RANDOM_UUID(),'PADRÃO',@GrupoSemaforicoId4,1,NOW(),NOW()),
  (@TabelaEntreVerdesId5 ,RANDOM_UUID(),'PADRÃO',@GrupoSemaforicoId3,1,NOW(),NOW()),
  (@TabelaEntreVerdesId6 ,RANDOM_UUID(),'PADRÃO',@GrupoSemaforicoId6,1, NOW(),NOW()),
  (@TabelaEntreVerdesId7 ,RANDOM_UUID(),'PADRÃO',@GrupoSemaforicoId10,1,NOW(),NOW()),
  (@TabelaEntreVerdesId8 ,RANDOM_UUID(),'PADRÃO',@GrupoSemaforicoId7,1, NOW(),NOW()),
  (@TabelaEntreVerdesId9 ,RANDOM_UUID(),'PADRÃO',@GrupoSemaforicoId9,1, NOW(),NOW()),
  (@TabelaEntreVerdesId10,RANDOM_UUID(),'PADRÃO',@GrupoSemaforicoId8,1, NOW(),NOW());
