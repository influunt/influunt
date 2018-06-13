# --- !Ups
alter table faixas_de_valores add column `tempo_ausencia_deteccao_pedestre_min` integer NOT NULL;
alter table faixas_de_valores add column `tempo_ausencia_deteccao_pedestre_max` integer NOT NULL;
alter table faixas_de_valores add column `tempo_deteccao_permanente_pedestre_min` integer NOT NULL;
alter table faixas_de_valores add column `tempo_deteccao_permanente_pedestre_max` integer NOT NULL;

# --- !Downs
alter table faixas_de_valores drop column `tempo_ausencia_deteccao_pedestre_min`;
alter table faixas_de_valores drop column `tempo_ausencia_deteccao_pedestre_max`;
alter table faixas_de_valores drop column `tempo_deteccao_permanente_pedestre_min`;
alter table faixas_de_valores drop column `tempo_deteccao_permanente_pedestre_max`;
