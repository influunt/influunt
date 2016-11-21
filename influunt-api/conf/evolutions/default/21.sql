# --- !Ups
DROP index ix_detectores_controlador_id on detectores;
ALTER TABLE detectores DROP COLUMN controlador_id;


# --- !Downs
ADD index ix_detectores_controlador_id on detectores;
ALTER TABLE detectores ADD COLUMN controlador_id TINYINT(1);
