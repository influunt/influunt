# --- !Ups

alter table faixas_de_valores add column default_tempo_maximo_permanencia_estagio_veicular integer not null AFTER tempo_maximo_permanencia_estagio_max;


# --- !Downs
alter table faixas_de_valores drop column default_tempo_maximo_permanencia_estagio_veicular;



