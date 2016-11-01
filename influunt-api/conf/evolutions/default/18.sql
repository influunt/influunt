# --- !Ups

ALTER TABLE controladores ADD COLUMN bloqueado TINYINT(1) NOT NULL DEFAULT 0 AFTER subarea_id;
ALTER TABLE controladores ADD COLUMN planos_bloqueado TINYINT(1) NOT NULL DEFAULT 0 AFTER bloqueado;


# --- !Downs

ALTER TABLE controladores DROP COLUMN planos_bloqueado;
ALTER TABLE controladores DROP COLUMN bloqueado;
