# --- !Ups
alter table planos add column `ciclo_duplo` tinyint(1) NOT NULL DEFAULT '0';
alter table planos add column `tempo_ciclo_duplo` integer;

# --- !Downs
alter table planos drop column `ciclo_duplo`;
alter table planos drop column `tempo_ciclo_duplo`;
