# --- !Ups

ALTER TABLE permissoes_app ADD COLUMN descricao varchar(255) NOT NULL DEFAULT "";


# --- !Downs

ALTER TABLE permissoes_app DROP COLUMN descricao
