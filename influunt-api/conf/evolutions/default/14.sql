# --- !Ups

alter table intervalos drop foreign key fk_intervalos_grupo_semaforico_plano_id;
drop index ix_intervalos_grupo_semaforico_plano_id on intervalos;

drop table if exists intervalos;
