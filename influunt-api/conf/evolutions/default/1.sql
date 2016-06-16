# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table aneis (
  id                            uuid not null,
  descricao                     varchar(255),
  id_anel                       varchar(255),
  numero_smee                   varchar(255),
  latitude                      double,
  longitude                     double,
  controlador_id                uuid,
  data_criacao                  timestamp,
  data_atualizacao              timestamp,
  constraint pk_aneis primary key (id)
);

create table areas (
  id                            uuid not null,
  descricao                     varchar(255),
  cidade_id                     uuid,
  data_criacao                  timestamp,
  data_atualizacao              timestamp,
  constraint pk_areas primary key (id)
);

create table cidades (
  id                            uuid not null,
  nome                          varchar(255),
  data_criacao                  timestamp,
  data_atualizacao              timestamp,
  constraint pk_cidades primary key (id)
);

create table configuracao_controladores (
  id                            uuid not null,
  limite_estagio                integer,
  limite_grupo_semaforico       integer,
  limite_anel                   integer,
  limite_detector_pedestre      integer,
  limite_detector_veicular      integer,
  data_criacao                  timestamp,
  data_atualizacao              timestamp,
  constraint pk_configuracao_controladores primary key (id)
);

create table controladores (
  id                            uuid not null,
  data_criacao                  timestamp,
  data_atualizacao              timestamp,
  descricao                     varchar(255),
  numero_smee                   varchar(255),
  id_controlador                varchar(255),
  numero_smeeconjugado1         varchar(255),
  numero_smeeconjugado2         varchar(255),
  numero_smeeconjugado3         varchar(255),
  firmware                      varchar(255),
  latitude                      double,
  longitude                     double,
  area_id                       uuid,
  constraint pk_controladores primary key (id)
);

create table detectores (
  id                            uuid not null,
  tipo_detector_id              uuid,
  anel_id                       uuid,
  data_criacao                  timestamp,
  data_atualizacao              timestamp,
  constraint pk_detectores primary key (id)
);

create table fabricantes (
  id                            uuid not null,
  nome                          varchar(255),
  data_criacao                  timestamp,
  data_atualizacao              timestamp,
  constraint pk_fabricantes primary key (id)
);

create table grupos_semaforicos (
  id                            uuid not null,
  tipo_id                       uuid,
  anel_id                       uuid,
  controlador_id                uuid,
  grupo_conflito_id             uuid,
  data_criacao                  timestamp,
  data_atualizacao              timestamp,
  constraint pk_grupos_semaforicos primary key (id)
);

create table limite_area (
  id                            uuid not null,
  latitude                      double,
  longitude                     double,
  area_id                       uuid,
  data_criacao                  timestamp,
  data_atualizacao              timestamp,
  constraint pk_limite_area primary key (id)
);

create table modelo_controladores (
  id                            uuid not null,
  fabricante_id                 uuid,
  configuracao_id               uuid,
  descricao                     varchar(255),
  data_criacao                  timestamp,
  data_atualizacao              timestamp,
  constraint pk_modelo_controladores primary key (id)
);

create table movimentos (
  id                            uuid not null,
  descricao                     varchar(255),
  anel_id                       uuid,
  data_criacao                  timestamp,
  data_atualizacao              timestamp,
  constraint pk_movimentos primary key (id)
);

create table tipos_detectores (
  id                            uuid not null,
  descricao                     varchar(255),
  data_criacao                  timestamp,
  data_atualizacao              timestamp,
  constraint pk_tipos_detectores primary key (id)
);

create table tipo_grupo_semaforicos (
  id                            uuid not null,
  descricao                     varchar(255),
  data_criacao                  timestamp,
  data_atualizacao              timestamp,
  constraint pk_tipo_grupo_semaforicos primary key (id)
);

alter table aneis add constraint fk_aneis_controlador_id foreign key (controlador_id) references controladores (id) on delete restrict on update restrict;
create index ix_aneis_controlador_id on aneis (controlador_id);

alter table areas add constraint fk_areas_cidade_id foreign key (cidade_id) references cidades (id) on delete restrict on update restrict;
create index ix_areas_cidade_id on areas (cidade_id);

alter table controladores add constraint fk_controladores_area_id foreign key (area_id) references areas (id) on delete restrict on update restrict;
create index ix_controladores_area_id on controladores (area_id);

alter table detectores add constraint fk_detectores_tipo_detector_id foreign key (tipo_detector_id) references tipos_detectores (id) on delete restrict on update restrict;
create index ix_detectores_tipo_detector_id on detectores (tipo_detector_id);

alter table detectores add constraint fk_detectores_anel_id foreign key (anel_id) references aneis (id) on delete restrict on update restrict;
create index ix_detectores_anel_id on detectores (anel_id);

alter table grupos_semaforicos add constraint fk_grupos_semaforicos_tipo_id foreign key (tipo_id) references tipo_grupo_semaforicos (id) on delete restrict on update restrict;
create index ix_grupos_semaforicos_tipo_id on grupos_semaforicos (tipo_id);

alter table grupos_semaforicos add constraint fk_grupos_semaforicos_anel_id foreign key (anel_id) references aneis (id) on delete restrict on update restrict;
create index ix_grupos_semaforicos_anel_id on grupos_semaforicos (anel_id);

alter table grupos_semaforicos add constraint fk_grupos_semaforicos_controlador_id foreign key (controlador_id) references controladores (id) on delete restrict on update restrict;
create index ix_grupos_semaforicos_controlador_id on grupos_semaforicos (controlador_id);

alter table grupos_semaforicos add constraint fk_grupos_semaforicos_grupo_conflito_id foreign key (grupo_conflito_id) references grupos_semaforicos (id) on delete restrict on update restrict;
create index ix_grupos_semaforicos_grupo_conflito_id on grupos_semaforicos (grupo_conflito_id);

alter table limite_area add constraint fk_limite_area_area_id foreign key (area_id) references areas (id) on delete restrict on update restrict;
create index ix_limite_area_area_id on limite_area (area_id);

alter table modelo_controladores add constraint fk_modelo_controladores_fabricante_id foreign key (fabricante_id) references fabricantes (id) on delete restrict on update restrict;
create index ix_modelo_controladores_fabricante_id on modelo_controladores (fabricante_id);

alter table modelo_controladores add constraint fk_modelo_controladores_configuracao_id foreign key (configuracao_id) references configuracao_controladores (id) on delete restrict on update restrict;
create index ix_modelo_controladores_configuracao_id on modelo_controladores (configuracao_id);

alter table movimentos add constraint fk_movimentos_anel_id foreign key (anel_id) references aneis (id) on delete restrict on update restrict;
create index ix_movimentos_anel_id on movimentos (anel_id);


# --- !Downs

alter table aneis drop foreign key fk_aneis_controlador_id;
drop index ix_aneis_controlador_id on aneis;

alter table areas drop constraint if exists fk_areas_cidade_id;
drop index if exists ix_areas_cidade_id;

alter table controladores drop constraint if exists fk_controladores_area_id;
drop index if exists ix_controladores_area_id;

alter table detectores drop constraint if exists fk_detectores_tipo_detector_id;
drop index if exists ix_detectores_tipo_detector_id;

alter table detectores drop constraint if exists fk_detectores_anel_id;
drop index if exists ix_detectores_anel_id;

alter table grupos_semaforicos drop constraint if exists fk_grupos_semaforicos_tipo_id;
drop index if exists ix_grupos_semaforicos_tipo_id;

alter table grupos_semaforicos drop constraint if exists fk_grupos_semaforicos_anel_id;
drop index if exists ix_grupos_semaforicos_anel_id;

alter table grupos_semaforicos drop constraint if exists fk_grupos_semaforicos_controlador_id;
drop index if exists ix_grupos_semaforicos_controlador_id;

alter table grupos_semaforicos drop constraint if exists fk_grupos_semaforicos_grupo_conflito_id;
drop index if exists ix_grupos_semaforicos_grupo_conflito_id;

alter table limite_area drop constraint if exists fk_limite_area_area_id;
drop index if exists ix_limite_area_area_id;

alter table modelo_controladores drop constraint if exists fk_modelo_controladores_fabricante_id;
drop index if exists ix_modelo_controladores_fabricante_id;

alter table modelo_controladores drop constraint if exists fk_modelo_controladores_configuracao_id;
drop index if exists ix_modelo_controladores_configuracao_id;

alter table movimentos drop constraint if exists fk_movimentos_anel_id;
drop index if exists ix_movimentos_anel_id;

drop table if exists aneis;

drop table if exists areas;

drop table if exists cidades;

drop table if exists configuracao_controladores;

drop table if exists controladores;

drop table if exists detectores;

drop table if exists fabricantes;

drop table if exists grupos_semaforicos;

drop table if exists limite_area;

drop table if exists modelo_controladores;

drop table if exists movimentos;

drop table if exists tipos_detectores;

drop table if exists tipo_grupo_semaforicos;

