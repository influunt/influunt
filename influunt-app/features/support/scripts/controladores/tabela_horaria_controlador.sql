DELETE FROM `eventos`;
DELETE FROM `tabela_horarios`;
DELETE FROM `versoes_tabelas_horarias`;

SET @ControladorID = (SELECT id FROM "controladores" where nome_endereco = 'Av. Paulista com R. Bela Cintra');
SET @TabelaHorariaID = RANDOM_UUID();
SET @VersaoTabelaHorariaId = RANDOM_UUID();
SET @UsurioId = (SELECT id FROM "usuarios" where login = 'mobilab');

INSERT INTO `versoes_tabelas_horarias` (`id`,`id_json`,`controlador_id`,`tabela_horaria_origem_id`,`usuario_id`,`descricao`,`status_versao`,`data_criacao`) VALUES
                                       (@VersaoTabelaHorariaId, RANDOM_UUID(), @ControladorID, NULL, @UsurioId,NULL,'EDITANDO',NOW());

INSERT INTO `tabela_horarios` (`id`,`id_json`,`versao_tabela_horaria_id`,`data_criacao`,`data_atualizacao`) VALUES
                              (@TabelaHorariaID, RANDOM_UUID(), @VersaoTabelaHorariaId, NOW(), NOW());

INSERT INTO `eventos` (`id`,`id_json`,`posicao`,`dia_da_semana`,`horario`,`data`,`nome`,`posicao_plano`,`tipo`,`tabela_horario_id`,`agrupamento_id`,`data_criacao`,`data_atualizacao`) VALUES
                      (RANDOM_UUID(),RANDOM_UUID(), 1,'DOMINGO','12:15:00', NOW(), NULL,1, 'NORMAL', @TabelaHorariaID, NULL, NOW(), NOW()),
                      (RANDOM_UUID(),RANDOM_UUID(), 2,'SEGUNDA','14:20:00', NOW(), NULL,2, 'NORMAL', @TabelaHorariaID, NULL, NOW(), NOW()),
                      (RANDOM_UUID(),RANDOM_UUID(), 3,'TERCA',  '10:02:00', NOW(), NULL,3, 'NORMAL', @TabelaHorariaID, NULL, NOW(), NOW()),
                      (RANDOM_UUID(),RANDOM_UUID(), 4,'QUARTA', '18:10:00', NOW(), NULL,4, 'NORMAL', @TabelaHorariaID, NULL, NOW(), NOW()),
                      (RANDOM_UUID(),RANDOM_UUID(), 5,'QUINTA', '15:20:00', NOW(), NULL,5, 'NORMAL', @TabelaHorariaID, NULL, NOW(), NOW());



