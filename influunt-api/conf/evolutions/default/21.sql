# --- !Ups
ALTER TABLE detectores DROP COLUMN controlador_id;


# --- !Downs
ALTER TABLE detectores ADD COLUMN controlador_id TINYINT(1);
