
DELETE FROM `estagios_planos`;
DELETE FROM `planos`;
DELETE FROM `versoes_planos`;


SET @VersaoPlanoId1 = RANDOM_UUID();
SET @VersaoPlanoId2 = RANDOM_UUID();
-- Aneis do controlador ja cadastrado
SET @anel1_id = '287415c8-1d96-4ab1-a04f-377b19cd9238';
SET @anel2_id = 'e4aea91d-790d-496f-b70e-887060734fd7';
SET @PlanoId1 = RANDOM_UUID();
SET @PlanoId2 = RANDOM_UUID();
SET @PlanoId3 = RANDOM_UUID();
SET @PlanoId4 = RANDOM_UUID();
SET @PlanoId5 = RANDOM_UUID();
SET @PlanoId6 = RANDOM_UUID();
SET @PlanoId7 = RANDOM_UUID();
SET @PlanoId8 = RANDOM_UUID();
SET @PlanoId9 = RANDOM_UUID();
SET @PlanoId10 = RANDOM_UUID();

INSERT INTO `versoes_planos` (`id`,`id_json`,`versao_anterior_id`,`anel_id`,`usuario_id`,`descricao`,`status_versao`,`data_criacao`, `data_atualizacao`) VALUES
                             (@VersaoPlanoId1, RANDOM_UUID(), null, @anel1_id, '90574f0a-cd7b-477c-9fa6-98c426813ea8', 'Planos criado', 'EDITANDO', NOW(), NOW()),
                             (@VersaoPlanoId2, RANDOM_UUID(), null, @anel2_id, '90574f0a-cd7b-477c-9fa6-98c426813ea8', 'Planos criado', 'EDITANDO', NOW(), NOW());

INSERT INTO `planos` (`id`, `id_json`,`posicao`,`descricao`,`tempo_ciclo`,`defasagem`,`versao_plano_id`,`modo_operacao`,`posicao_tabela_entre_verde`,`data_criacao`,`data_atualizacao`) VALUES
                     (@PlanoId1,RANDOM_UUID(),1,'PLANO 77',45,0,@VersaoPlanoId1,0, 1, NOW(), NOW()),
                     (@PlanoId2,RANDOM_UUID(),2,'PLANO 2',50,0,@VersaoPlanoId1, 1, 1, NOW(), NOW()),
                     (@PlanoId3,RANDOM_UUID(),3,'PLANO 3',30,0,@VersaoPlanoId1, 4, 1, NOW(), NOW()),
                     (@PlanoId4,RANDOM_UUID(),4,'PLANO 4',30,0,@VersaoPlanoId1, 3, 1, NOW(), NOW()),
                     (@PlanoId5,RANDOM_UUID(),5,'PLANO 5',0 ,0,@VersaoPlanoId1, 2, 1, NOW(), NOW()),
                     (@PlanoId6,RANDOM_UUID(),1,'PLANO 1',45,0,@VersaoPlanoId2, 0, 1, NOW(), NOW()),
                     (@PlanoId7,RANDOM_UUID(),2,'PLANO 2',30,0,@VersaoPlanoId2, 0, 1, NOW(), NOW()),
                     (@PlanoId8,RANDOM_UUID(),3,'PLANO 3',30,0,@VersaoPlanoId2, 4, 1, NOW(), NOW()),
                     (@PlanoId9,RANDOM_UUID(),4,'PLANO 4',30,0,@VersaoPlanoId2, 3, 1, NOW(), NOW()),
                     (@PlanoId10,RANDOM_UUID(),5,'PLANO 5',0,0,@VersaoPlanoId2, 2, 1, NOW(), NOW());


-- estágio_id já j[a está cadastrado pelo arquivo controlador.sql
INSERT INTO `estagios_planos` (`id`,`id_json`,`estagio_id`,`plano_id`,`posicao`,`tempo_verde`,`tempo_verde_minimo`,`tempo_verde_maximo`,`tempo_verde_intermediario`,`tempo_extensao_verde`,`dispensavel`,`estagio_que_recebe_estagio_dispensavel_id`,`data_criacao`,`data_atualizacao`) VALUES
                              (RANDOM_UUID(),RANDOM_UUID(),'63fc4b2b-85be-4900-a374-0e85fc7b1e85',@PlanoId1,1,12,0,0,0,0.0,FALSE,null,NOW(),NOW()),
                              (RANDOM_UUID(),RANDOM_UUID(),'a76a7b05-289b-460e-aad6-4901eb2cd1ba',@PlanoId1,2,13,0,0,0,0.0,FALSE,null,NOW(),NOW()),
                              (RANDOM_UUID(),RANDOM_UUID(),'12b2a949-6ab0-4467-b30a-be7c2ff92b05',@PlanoId1,3,11,null,null,null,null,FALSE,null,NOW(),NOW()),
                              (RANDOM_UUID(),RANDOM_UUID(),'12b2a949-6ab0-4467-b30a-be7c2ff92b05',@PlanoId2,1,14,0,0,0,0.0,FALSE,null,NOW(),NOW()),
                              (RANDOM_UUID(),RANDOM_UUID(),'63fc4b2b-85be-4900-a374-0e85fc7b1e85',@PlanoId2,2,14,0,0,0,0.0,FALSE,null,NOW(),NOW()),
                              (RANDOM_UUID(),RANDOM_UUID(),'a76a7b05-289b-460e-aad6-4901eb2cd1ba',@PlanoId2,3,13,0,0,0,0.0,FALSE,null,NOW(),NOW()),
                              (RANDOM_UUID(),RANDOM_UUID(),'12b2a949-6ab0-4467-b30a-be7c2ff92b05',@PlanoId3,1,1,0,0,0,0.0,FALSE,null,NOW(),NOW()),
                              (RANDOM_UUID(),RANDOM_UUID(),'63fc4b2b-85be-4900-a374-0e85fc7b1e85',@PlanoId3,2,1,0,0,0,0.0,FALSE,null,NOW(),NOW()),
                              (RANDOM_UUID(),RANDOM_UUID(),'a76a7b05-289b-460e-aad6-4901eb2cd1ba',@PlanoId3,3,1,0,0,0,0.0,FALSE,null,NOW(),NOW()),
                              (RANDOM_UUID(),RANDOM_UUID(),'12b2a949-6ab0-4467-b30a-be7c2ff92b05',@PlanoId4,1,1,0,0,0,0.0,FALSE,null,NOW(),NOW()),
                              (RANDOM_UUID(),RANDOM_UUID(),'63fc4b2b-85be-4900-a374-0e85fc7b1e85',@PlanoId4,2,1,0,0,0,0.0,FALSE,null,NOW(),NOW()),
                              (RANDOM_UUID(),RANDOM_UUID(),'a76a7b05-289b-460e-aad6-4901eb2cd1ba',@PlanoId4,3,1,0,0,0,0.0,FALSE,null,NOW(),NOW()),
                              (RANDOM_UUID(),RANDOM_UUID(),'12b2a949-6ab0-4467-b30a-be7c2ff92b05',@PlanoId5,1,0,10,20,15,5.0,FALSE,null,NOW(),NOW()),
                              (RANDOM_UUID(),RANDOM_UUID(),'63fc4b2b-85be-4900-a374-0e85fc7b1e85',@PlanoId5,2,0,10,20,15,2.0,FALSE,null,NOW(),NOW()),
                              (RANDOM_UUID(),RANDOM_UUID(),'a76a7b05-289b-460e-aad6-4901eb2cd1ba',@PlanoId5,3,0,10,34,11,5.0,FALSE,null,NOW(),NOW()),
                              (RANDOM_UUID(),RANDOM_UUID(),'108ed266-d7b4-461f-b4e9-e2117c3348a6',@PlanoId6,1,18,null,null,null,null,FALSE,null,NOW(),NOW()),
                              (RANDOM_UUID(),RANDOM_UUID(),'28e7a329-ca3c-4431-916d-d2aa2283f7e6',@PlanoId6,2,21,null,null,null,null,FALSE,null,NOW(),NOW()),
                              (RANDOM_UUID(),RANDOM_UUID(),'108ed266-d7b4-461f-b4e9-e2117c3348a6',@PlanoId7,1,12,null,null,null,null,FALSE,null,NOW(),NOW()),
                              (RANDOM_UUID(),RANDOM_UUID(),'28e7a329-ca3c-4431-916d-d2aa2283f7e6',@PlanoId7,2,12,null,null,null,null,FALSE,null,NOW(),NOW()),
                              (RANDOM_UUID(),RANDOM_UUID(),'108ed266-d7b4-461f-b4e9-e2117c3348a6',@PlanoId8,1,1,0,0,0,0.0,FALSE,null,NOW(),NOW()),
                              (RANDOM_UUID(),RANDOM_UUID(),'28e7a329-ca3c-4431-916d-d2aa2283f7e6',@PlanoId8,2,1,0,0,0,0.0,FALSE,null,NOW(),NOW()),
                              (RANDOM_UUID(),RANDOM_UUID(),'108ed266-d7b4-461f-b4e9-e2117c3348a6',@PlanoId9,1,1,0,0,0,0.0,FALSE,null,NOW(),NOW()),
                              (RANDOM_UUID(),RANDOM_UUID(),'28e7a329-ca3c-4431-916d-d2aa2283f7e6',@PlanoId9,2,1,0,0,0,0.0,FALSE,null,NOW(),NOW()),
                              (RANDOM_UUID(),RANDOM_UUID(),'108ed266-d7b4-461f-b4e9-e2117c3348a6',@PlanoId10,1,0,10,20,15,10.0,FALSE,null,NOW(),NOW()),
                              (RANDOM_UUID(),RANDOM_UUID(),'28e7a329-ca3c-4431-916d-d2aa2283f7e6',@PlanoId10,2,0,10,20,15,2.0,FALSE,null,NOW(),NOW());

INSERT INTO `grupos_semaforicos_planos` (`id`,`id_json`,`grupo_semaforico_id`,`plano_id`,`ativado`,`data_criacao`,`data_atualizacao`) VALUES
                                        (RANDOM_UUID(),RANDOM_UUID(),'4ac5742a-7357-4cb0-9801-0075132c9d9f',@PlanoId1,TRUE,NOW(),NOW()),
                                        (RANDOM_UUID(),RANDOM_UUID(),'a841dc7c-837e-46be-a46e-5ba8e1d27747',@PlanoId1,TRUE,NOW(),NOW()),
                                        (RANDOM_UUID(),RANDOM_UUID(),'b2425ed0-dbd1-4591-bde7-82c6a18dfe3a',@PlanoId1,TRUE,NOW(),NOW()),
                                        (RANDOM_UUID(),RANDOM_UUID(),'4ac5742a-7357-4cb0-9801-0075132c9d9f',@PlanoId2,TRUE,NOW(),NOW()),
                                        (RANDOM_UUID(),RANDOM_UUID(),'a841dc7c-837e-46be-a46e-5ba8e1d27747',@PlanoId2,TRUE,NOW(),NOW()),
                                        (RANDOM_UUID(),RANDOM_UUID(),'b2425ed0-dbd1-4591-bde7-82c6a18dfe3a',@PlanoId2,TRUE,NOW(),NOW()),
                                        (RANDOM_UUID(),RANDOM_UUID(),'4ac5742a-7357-4cb0-9801-0075132c9d9f',@PlanoId3,TRUE,NOW(),NOW()),
                                        (RANDOM_UUID(),RANDOM_UUID(),'a841dc7c-837e-46be-a46e-5ba8e1d27747',@PlanoId3,TRUE,NOW(),NOW()),
                                        (RANDOM_UUID(),RANDOM_UUID(),'b2425ed0-dbd1-4591-bde7-82c6a18dfe3a',@PlanoId3,TRUE,NOW(),NOW()),
                                        (RANDOM_UUID(),RANDOM_UUID(),'4ac5742a-7357-4cb0-9801-0075132c9d9f',@PlanoId4,TRUE,NOW(),NOW()),
                                        (RANDOM_UUID(),RANDOM_UUID(),'a841dc7c-837e-46be-a46e-5ba8e1d27747',@PlanoId4,TRUE,NOW(),NOW()),
                                        (RANDOM_UUID(),RANDOM_UUID(),'b2425ed0-dbd1-4591-bde7-82c6a18dfe3a',@PlanoId4,TRUE,NOW(),NOW()),
                                        (RANDOM_UUID(),RANDOM_UUID(),'4ac5742a-7357-4cb0-9801-0075132c9d9f',@PlanoId5,TRUE,NOW(),NOW()),
                                        (RANDOM_UUID(),RANDOM_UUID(),'a841dc7c-837e-46be-a46e-5ba8e1d27747',@PlanoId5,TRUE,NOW(),NOW()),
                                        (RANDOM_UUID(),RANDOM_UUID(),'b2425ed0-dbd1-4591-bde7-82c6a18dfe3a',@PlanoId5,TRUE,NOW(),NOW()),
                                        (RANDOM_UUID(),RANDOM_UUID(),'227858c0-248f-4551-aeaf-6c3119fdb65f',@PlanoId6,TRUE,NOW(),NOW()),
                                        (RANDOM_UUID(),RANDOM_UUID(),'d20ca39b-9e50-42b5-8e6a-6fddfb2ba7ef',@PlanoId6,TRUE,NOW(),NOW()),
                                        (RANDOM_UUID(),RANDOM_UUID(),'227858c0-248f-4551-aeaf-6c3119fdb65f',@PlanoId7,TRUE,NOW(),NOW()),
                                        (RANDOM_UUID(),RANDOM_UUID(),'d20ca39b-9e50-42b5-8e6a-6fddfb2ba7ef',@PlanoId7,TRUE,NOW(),NOW()),
                                        (RANDOM_UUID(),RANDOM_UUID(),'227858c0-248f-4551-aeaf-6c3119fdb65f',@PlanoId8,TRUE,NOW(),NOW()),
                                        (RANDOM_UUID(),RANDOM_UUID(),'d20ca39b-9e50-42b5-8e6a-6fddfb2ba7ef',@PlanoId8,TRUE,NOW(),NOW()),
                                        (RANDOM_UUID(),RANDOM_UUID(),'227858c0-248f-4551-aeaf-6c3119fdb65f',@PlanoId9,TRUE,NOW(),NOW()),
                                        (RANDOM_UUID(),RANDOM_UUID(),'d20ca39b-9e50-42b5-8e6a-6fddfb2ba7ef',@PlanoId9,TRUE,NOW(),NOW()),
                                        (RANDOM_UUID(),RANDOM_UUID(),'227858c0-248f-4551-aeaf-6c3119fdb65f',@PlanoId10,TRUE,NOW(),NOW()),
                                        (RANDOM_UUID(),RANDOM_UUID(),'d20ca39b-9e50-42b5-8e6a-6fddfb2ba7ef',@PlanoId10,TRUE,NOW(),NOW());
