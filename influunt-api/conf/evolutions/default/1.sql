# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table aneis (
  id                            varchar(40) not null,
  descricao                     varchar(255),
  id_anel                       varchar(255),
  numero_smee                   varchar(255),
  latitude                      double,
  longitude                     double,
  controlador_id                varchar(40),
  data_criacao                  datetime(6),
  data_atualizacao              datetime(6),
  constraint pk_aneis primary key (id)
);

create table areas (
  id                            varchar(40) not null,
  descricao                     varchar(255),
  cidade_id                     varchar(40),
  data_criacao                  datetime(6),
  data_atualizacao              datetime(6),
  constraint pk_areas primary key (id)
);

create table cidades (
  id                            varchar(40) not null,
  nome                          varchar(255),
  data_criacao                  datetime(6),
  data_atualizacao              datetime(6),
  constraint pk_cidades primary key (id)
);

create table configuracao_controladores (
  id                            varchar(40) not null,
  limite_estagio                integer,
  limite_grupo_semaforico       integer,
  limite_anel                   integer,
  limite_detector_pedestre      integer,
  limite_detector_veicular      integer,
  data_criacao                  datetime(6),
  data_atualizacao              datetime(6),
  constraint pk_configuracao_controladores primary key (id)
);

create table controladores (
  id                            varchar(40) not null,
  data_criacao                  datetime(6),
  data_atualizacao              datetime(6),
  descricao                     varchar(255),
  numero_smee                   varchar(255),
  id_controlador                varchar(255),
  numero_smeeconjugado1         varchar(255),
  numero_smeeconjugado2         varchar(255),
  numero_smeeconjugado3         varchar(255),
  firmware                      varchar(255),
  latitude                      double,
  longitude                     double,
  area_id                       varchar(40),
  constraint pk_controladores primary key (id)
);

create table detectores (
  id                            varchar(40) not null,
  tipo_detector_id              varchar(40),
  anel_id                       varchar(40),
  data_criacao                  datetime(6),
  data_atualizacao              datetime(6),
  constraint pk_detectores primary key (id)
);

create table fabricantes (
  id                            varchar(40) not null,
  nome                          varchar(255),
  data_criacao                  datetime(6),
  data_atualizacao              datetime(6),
  constraint pk_fabricantes primary key (id)
);

create table grupos_semaforicos (
  id                            varchar(40) not null,
  tipo_id                       varchar(40),
  anel_id                       varchar(40),
  controlador_id                varchar(40),
  grupo_conflito_id             varchar(40),
  data_criacao                  datetime(6),
  data_atualizacao              datetime(6),
  constraint pk_grupos_semaforicos primary key (id)
);

create table limite_area (
  id                            varchar(40) not null,
  latitude                      double,
  longitude                     double,
  area_id                       varchar(40),
  data_criacao                  datetime(6),
  data_atualizacao              datetime(6),
  constraint pk_limite_area primary key (id)
);

create table modelo_controladores (
  id                            varchar(40) not null,
  fabricante_id                 varchar(40),
  configuracao_id               varchar(40),
  descricao                     varchar(255),
  data_criacao                  datetime(6),
  data_atualizacao              datetime(6),
  constraint pk_modelo_controladores primary key (id)
);

create table movimentos (
  id                            varchar(40) not null,
  descricao                     varchar(255),
  anel_id                       varchar(40),
  data_criacao                  datetime(6),
  data_atualizacao              datetime(6),
  constraint pk_movimentos primary key (id)
);

create table tipos_detectores (
  id                            varchar(40) not null,
  descricao                     varchar(255),
  data_criacao                  datetime(6),
  data_atualizacao              datetime(6),
  constraint pk_tipos_detectores primary key (id)
);

create table tipo_grupo_semaforicos (
  id                            varchar(40) not null,
  descricao                     varchar(255),
  data_criacao                  datetime(6),
  data_atualizacao              datetime(6),
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

alter table areas drop foreign key fk_areas_cidade_id;
drop index ix_areas_cidade_id on areas;

alter table controladores drop foreign key fk_controladores_area_id;
drop index ix_controladores_area_id on controladores;

alter table detectores drop foreign key fk_detectores_tipo_detector_id;
drop index ix_detectores_tipo_detector_id on detectores;

alter table detectores drop foreign key fk_detectores_anel_id;
drop index ix_detectores_anel_id on detectores;

alter table grupos_semaforicos drop foreign key fk_grupos_semaforicos_tipo_id;
drop index ix_grupos_semaforicos_tipo_id on grupos_semaforicos;

alter table grupos_semaforicos drop foreign key fk_grupos_semaforicos_anel_id;
drop index ix_grupos_semaforicos_anel_id on grupos_semaforicos;

alter table grupos_semaforicos drop foreign key fk_grupos_semaforicos_controlador_id;
drop index ix_grupos_semaforicos_controlador_id on grupos_semaforicos;

alter table grupos_semaforicos drop foreign key fk_grupos_semaforicos_grupo_conflito_id;
drop index ix_grupos_semaforicos_grupo_conflito_id on grupos_semaforicos;

alter table limite_area drop foreign key fk_limite_area_area_id;
drop index ix_limite_area_area_id on limite_area;

alter table modelo_controladores drop foreign key fk_modelo_controladores_fabricante_id;
drop index ix_modelo_controladores_fabricante_id on modelo_controladores;

alter table modelo_controladores drop foreign key fk_modelo_controladores_configuracao_id;
drop index ix_modelo_controladores_configuracao_id on modelo_controladores;

alter table movimentos drop foreign key fk_movimentos_anel_id;
drop index ix_movimentos_anel_id on movimentos;

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

