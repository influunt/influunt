# --- !Ups

ALTER TABLE sessoes ADD COLUMN data_saida datetime(6);
ALTER TABLE sessoes ADD COLUMN ip VARCHAR (20);

# --- !Downs

ALTER TABLE sessoes DROP COLUMN data_saida;
ALTER TABLE sessoes DROP COLUMN ip;