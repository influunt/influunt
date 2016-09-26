# --- !Ups

create table faixas_de_valores (
  id                            varchar(40) not null,
  id_json                       varchar(255),
  tempo_defasagem_min           integer not null,
  tempo_defasagem_max           integer not null,
  tempo_amarelo_min             integer not null,
  tempo_amarelo_max             integer not null,
  tempo_vermelho_intermitente_min integer not null,
  tempo_vermelho_intermitente_max integer not null,
  tempo_vermelho_limpeza_veicular_min integer not null,
  tempo_vermelho_limpeza_veicular_max integer not null,
  tempo_vermelho_limpeza_pedestre_min integer not null,
  tempo_vermelho_limpeza_pedestre_max integer not null,
  tempo_atraso_grupo_min        integer not null,
  tempo_atraso_grupo_max        integer not null,
  tempo_verde_seguranca_veicular_min integer not null,
  tempo_verde_seguranca_veicular_max integer not null,
  tempo_verde_seguranca_pedestre_min integer not null,
  tempo_verde_seguranca_pedestre_max integer not null,
  tempo_maximo_permanencia_estagio_min integer not null,
  tempo_maximo_permanencia_estagio_max integer not null,
  tempo_ciclo_min               integer not null,
  tempo_ciclo_max               integer not null,
  tempo_verde_minimo_min        integer not null,
  tempo_verde_minimo_max        integer not null,
  tempo_verde_maximo_min        integer not null,
  tempo_verde_maximo_max        integer not null,
  tempo_verde_intermediario_min integer not null,
  tempo_verde_intermediario_max integer not null,
  tempo_extensao_verde_min      double not null,
  tempo_extensao_verde_max      double not null,
  tempo_verde_min               integer not null,
  tempo_verde_max               integer not null,
  tempo_ausencia_deteccao_min   integer not null,
  tempo_ausencia_deteccao_max   integer not null,
  tempo_deteccao_permanente_min integer not null,
  tempo_deteccao_permanente_max integer not null,
  data_criacao                  datetime(6) not null,
  data_atualizacao              datetime(6) not null,
  constraint pk_faixas_de_valores primary key (id)
);


# --- !Downs

drop table if exists faixas_de_valores;
