# --- !Ups

ALTER TABLE `eventos` ADD COLUMN `agrupamento_id` VARCHAR(40) NULL AFTER `tabela_horario_id`;

alter table eventos add constraint fk_eventos_agrupamento_id foreign key (agrupamento_id) references agrupamentos (id) on delete restrict on update restrict;
create index ix_eventos_agrupamento_id on eventos (agrupamento_id);



# --- !Downs

alter table eventos drop foreign key fk_eventos_agrupamento_id;
drop index ix_eventos_agrupamento_id on eventos;

ALTER TABLE `eventos` DROP COLUMN `agrupamento_id`



