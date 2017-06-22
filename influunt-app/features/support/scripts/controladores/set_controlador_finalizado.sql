-- Seta o controlador para configurado
SET @controldorID = '3d86335e-05e7-4921-8cdf-42ed03821f62';
SET @anelID1 = '287415c8-1d96-4ab1-a04f-377b19cd9238';
SET @anelID2 = 'e4aea91d-790d-496f-b70e-887060734fd7  ';

update versoes_tabelas_horarias set status_versao = 'CONFIGURADO' where controlador_id = @controldorID;
update versoes_planos set status_versao = 'CONFIGURADO' where anel_id = @anelID1;
update versoes_planos set status_versao = 'CONFIGURADO' where anel_id = @anelID2;
update controladores set numero_smee = '1234' where id = @controldorID;
update versoes_controladores set status_versao = 'CONFIGURADO' where controlador_id = @controldorID;
