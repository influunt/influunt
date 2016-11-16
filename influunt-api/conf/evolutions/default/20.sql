#-- !Ups
ALTER TABLE versoes_controladores ADD COLUMN data_atualizacao datetime(6);
ALTER TABLE versoes_planos ADD COLUMN data_atualizacao datetime(6);
ALTER TABLE versoes_tabelas_horarias ADD COLUMN data_atualizacao datetime(6);

# --- !Downs
ALTER TABLE versoes_controladores DROP COLUMN data_atualizacao;
ALTER TABLE versoes_planos DROP COLUMN data_atualizacao;
ALTER TABLE versoes_tabelas_horarias DROP COLUMN data_atualizacao;
