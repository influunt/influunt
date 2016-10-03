# --- !Ups

ALTER TABLE aneis ADD COLUMN aceita_modo_manual TINYINT(1) NOT NULL DEFAULT '0';



# --- !Downs

ALTER TABLE aneis DROP COLUMN aceita_modo_manual
