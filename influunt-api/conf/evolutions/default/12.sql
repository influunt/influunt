# --- !Ups
ALTER TABLE controladores ADD COLUMN croqui_id VARCHAR(40) NULL DEFAULT NULL AFTER firmware;
ALTER TABLE controladores ADD CONSTRAINT ix_controladores_croqui_id UNIQUE (croqui_id);
ALTER TABLE controladores ADD CONSTRAINT fk_controladores_croqui_id FOREIGN KEY (croqui_id) REFERENCES imagens (id) ON DELETE RESTRICT ON UPDATE RESTRICT;


# --- !Downs
ALTER TABLE controladores DROP FOREIGN KEY fk_controladores_croqui_id;
ALTER TABLE controladores DROP INDEX ix_controladores_croqui_id;
ALTER TABLE controladores DROP COLUMN croqui_id;


