# --- !Ups

alter table estagios add column tempo_verde_demanda_prioritaria integer AFTER demanda_prioritaria;


# --- !Downs
alter table estagios drop column tempo_verde_demanda_prioritaria;