# --- !Ups

ALTER TABLE controladores DROP COLUMN status_controlador;


# --- !Downs

ALTER TABLE controladores ADD COLUMN status_controlador INT(11) NULL DEFAULT NULL AFTER nome_endereco;
ALTER TABLE controladores ADD CONSTRAINT ck_controladores_status_controlador check (status_controlador in (0,1,2,3));


