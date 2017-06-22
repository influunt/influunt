# --- !Ups

alter table detectores drop foreign key fk_detectores_controlador_id;
DROP index ix_detectores_controlador_id on detectores;
ALTER TABLE detectores DROP COLUMN controlador_id;


# --- !Downs

ALTER TABLE detectores ADD COLUMN controlador_id TINYINT(1);
alter table detectores add constraint fk_detectores_controlador_id foreign key (controlador_id) references controladores (id) on delete restrict on update restrict;
create index ix_detectores_controlador_id on detectores (controlador_id);
