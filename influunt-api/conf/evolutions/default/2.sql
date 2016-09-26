# --- !Ups

ALTER TABLE sessoes ADD COLUMN data_saida datetime(6);
ALTER TABLE sessoes ADD COLUMN ip VARCHAR (20);

ALTER TABLE `controladores_fisicos` ADD COLUMN `area_id` VARCHAR(40) NOT NULL AFTER `id_json`;

UPDATE controladores_fisicos
SET controladores_fisicos.area_id = (
    SELECT controladores.area_id
    FROM controladores
    WHERE controladores.id = (
        SELECT versoes_controladores.controlador_id
        FROM versoes_controladores
        WHERE versoes_controladores.controlador_fisico_id = controladores_fisicos.id
));

alter table controladores_fisicos add constraint fk_controladores_fisicos_area_id foreign key (area_id) references areas (id) on delete restrict on update restrict;
create index ix_controladores_fisicos_area_id on controladores_fisicos (area_id);

# --- !Downs

alter table controladores_fisicos drop foreign key fk_controladores_fisicos_area_id;
drop index ix_controladores_fisicos_area_id on controladores_fisicos;

ALTER TABLE `controladores_fisicos` DROP COLUMN `area_id`;

ALTER TABLE sessoes DROP COLUMN ip;
ALTER TABLE sessoes DROP COLUMN data_saida;
