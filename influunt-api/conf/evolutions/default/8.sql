# --- !Ups

ALTER TABLE `versoes_controladores` CHANGE COLUMN `status_versao` `status_versao` VARCHAR(100) NULL DEFAULT NULL ;

UPDATE versoes_controladores SET status_versao = 'EDITANDO' WHERE status_versao = '0';
UPDATE versoes_controladores SET status_versao = 'CONFIGURADO' WHERE status_versao = '1';
UPDATE versoes_controladores SET status_versao = 'ARQUIVADO' WHERE status_versao = '2';

ALTER TABLE `versoes_planos` CHANGE COLUMN `status_versao` `status_versao` VARCHAR(100) NULL DEFAULT NULL ;

UPDATE versoes_planos SET status_versao = 'EDITANDO' WHERE status_versao = '0';
UPDATE versoes_planos SET status_versao = 'CONFIGURADO' WHERE status_versao = '1';
UPDATE versoes_planos SET status_versao = 'ARQUIVADO' WHERE status_versao = '2';


ALTER TABLE `versoes_tabelas_horarias` CHANGE COLUMN `status_versao` `status_versao` VARCHAR(100) NULL DEFAULT NULL ;

UPDATE versoes_tabelas_horarias SET status_versao = 'EDITANDO' WHERE status_versao = '0';
UPDATE versoes_tabelas_horarias SET status_versao = 'CONFIGURADO' WHERE status_versao = '1';
UPDATE versoes_tabelas_horarias SET status_versao = 'ARQUIVADO' WHERE status_versao = '2';


# --- !Downs

UPDATE versoes_controladores SET status_versao = '0' WHERE status_versao = 'EDITANDO';
UPDATE versoes_controladores SET status_versao = '1' WHERE status_versao = 'CONFIGURADO';
UPDATE versoes_controladores SET status_versao = '2' WHERE status_versao = 'ARQUIVADO';

ALTER TABLE `versoes_controladores` CHANGE COLUMN `status_versao` `status_versao` INTEGER NULL DEFAULT NULL ;


UPDATE versoes_planos SET status_versao = '0' WHERE status_versao = 'EDITANDO';
UPDATE versoes_planos SET status_versao = '1' WHERE status_versao = 'CONFIGURADO';
UPDATE versoes_planos SET status_versao = '2' WHERE status_versao = 'ARQUIVADO';

ALTER TABLE `versoes_planos` CHANGE COLUMN `status_versao` `status_versao` INTEGER NULL DEFAULT NULL ;

UPDATE versoes_tabelas_horarias SET status_versao = '0' WHERE status_versao = 'EDITANDO';
UPDATE versoes_tabelas_horarias SET status_versao = '1' WHERE status_versao = 'CONFIGURADO';
UPDATE versoes_tabelas_horarias SET status_versao = '2' WHERE status_versao = 'ARQUIVADO';

ALTER TABLE `versoes_tabelas_horarias` CHANGE COLUMN `status_versao` `status_versao` INTEGER NULL DEFAULT NULL ;

