# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table aneis (
  id                            uuid not null,
  ativo                         boolean not null,
  descricao                     varchar(255),
  posicao                       integer,
  numero_smee                   varchar(255),
  latitude                      double,
  longitude                     double,
  quantidade_grupo_pedestre     integer,
  quantidade_grupo_veicular     integer,
  quantidade_detector_pedestre  integer,
  quantidade_detector_veicular  integer,
  controlador_id                uuid,
  data_criacao                  timestamp not null,
  data_atualizacao              timestamp not null,
  constraint pk_aneis primary key (id)
);

create table areas (
  id                            uuid not null,
  descricao                     integer not null,
  cidade_id                     uuid not null,
  data_criacao                  timestamp not null,
  data_atualizacao              timestamp not null,
  constraint pk_areas primary key (id)
);

create table cidades (
  id                            uuid not null,
  nome                          varchar(255),
  data_criacao                  timestamp not null,
  data_atualizacao              timestamp not null,
  constraint pk_cidades primary key (id)
);

create table configuracao_controladores (
  id                            uuid not null,
  descricao                     varchar(255),
  limite_estagio                integer not null,
  limite_grupo_semaforico       integer not null,
  limite_anel                   integer not null,
  limite_detector_pedestre      integer not null,
  limite_detector_veicular      integer not null,
  data_criacao                  timestamp not null,
  data_atualizacao              timestamp not null,
  constraint pk_configuracao_controladores primary key (id)
);

create table controladores (
  id                            uuid not null,
  localizacao                   varchar(255),
  numero_smee                   varchar(255),
  numero_smeeconjugado1         varchar(255),
  numero_smeeconjugado2         varchar(255),
  numero_smeeconjugado3         varchar(255),
  firmware                      varchar(255),
  latitude                      double not null,
  longitude                     double not null,
  modelo_id                     uuid not null,
  area_id                       uuid not null,
  data_criacao                  timestamp not null,
  data_atualizacao              timestamp not null,
  constraint pk_controladores primary key (id)
);

create table detectores (
  id                            uuid not null,
  tipo                          varchar(8),
  anel_id                       uuid,
  controlador_id                uuid,
  monitorado                    boolean,
  tempo_ausecia_deteccao_minima integer,
  tempo_ausecia_deteccao_maxima integer,
  tempo_deteccao_permanente_minima integer,
  tempo_deteccao_permanente_maxima integer,
  data_criacao                  timestamp not null,
  data_atualizacao              timestamp not null,
  constraint ck_detectores_tipo check (tipo in ('VEICULAR','PEDESTRE')),
  constraint pk_detectores primary key (id)
);

create table estagios (
  id                            uuid not null,
  imagem_id                     uuid,
  descricao                     varchar(255),
  tempo_maximo_permanencia      integer,
  demanda_prioritaria           boolean,
  anel_id                       uuid,
  controlador_id                uuid,
  data_criacao                  timestamp not null,
  data_atualizacao              timestamp not null,
  constraint uq_estagios_imagem_id unique (imagem_id),
  constraint pk_estagios primary key (id)
);

create table estagios_grupos_semaforicos (
  id                            uuid not null,
  ativo                         boolean not null,
  estagio_id                    uuid not null,
  grupo_semaforico_id           uuid not null,
  data_criacao                  timestamp not null,
  data_atualizacao              timestamp not null,
  constraint pk_estagios_grupos_semaforicos primary key (id)
);

create table fabricantes (
  id                            uuid not null,
  nome                          varchar(255),
  data_criacao                  timestamp not null,
  data_atualizacao              timestamp not null,
  constraint pk_fabricantes primary key (id)
);

create table grupos_semaforicos (
  id                            uuid not null,
  tipo                          varchar(8),
  descricao                     varchar(255),
  anel_id                       uuid,
  controlador_id                uuid,
  grupo_conflito_id             uuid,
  posicao                       integer,
  data_criacao                  timestamp not null,
  data_atualizacao              timestamp not null,
  constraint ck_grupos_semaforicos_tipo check (tipo in ('PEDESTRE','VEICULAR')),
  constraint pk_grupos_semaforicos primary key (id)
);

create table imagens (
  id                            uuid not null,
  filename                      varchar(255),
  content_type                  varchar(255),
  data_criacao                  timestamp not null,
  data_atualizacao              timestamp not null,
  constraint pk_imagens primary key (id)
);

create table limite_area (
  id                            uuid not null,
  latitude                      double,
  longitude                     double,
  area_id                       uuid,
  data_criacao                  timestamp not null,
  data_atualizacao              timestamp not null,
  constraint pk_limite_area primary key (id)
);

create table modelo_controladores (
  id                            uuid not null,
  fabricante_id                 uuid not null,
  configuracao_id               uuid not null,
  descricao                     varchar(255) not null,
  data_criacao                  timestamp not null,
  data_atualizacao              timestamp not null,
  constraint pk_modelo_controladores primary key (id)
);

create table perfis (
  id                            uuid not null,
  nome                          varchar(255),
  data_criacao                  timestamp not null,
  data_atualizacao              timestamp not null,
  constraint pk_perfis primary key (id)
);

create table perfis_permissoes (
  perfil_id                     uuid not null,
  permissao_id                  uuid not null,
  constraint pk_perfis_permissoes primary key (perfil_id,permissao_id)
);

create table permissoes (
  id                            uuid not null,
  descricao                     varchar(255),
  chave                         varchar(255),
  data_criacao                  timestamp not null,
  data_atualizacao              timestamp not null,
  constraint pk_permissoes primary key (id)
);

create table permissoes_perfis (
  permissoes_id                 uuid not null,
  perfis_id                     uuid not null,
  constraint pk_permissoes_perfis primary key (permissoes_id,perfis_id)
);

create table sessoes (
  id                            uuid not null,
  usuario_login                 varchar(255),
  ativa                         boolean,
  data_criacao                  timestamp not null,
  constraint pk_sessoes primary key (id)
);

create table tabela_entre_verdes (
  id                            uuid not null,
  grupo_semaforico_id           uuid,
  constraint pk_tabela_entre_verdes primary key (id)
);

create table transicao (
  id                            uuid not null,
  tabela_entre_verdes_id        uuid,
  origem_id                     uuid,
  destino_id                    uuid,
  tempo_amarelo                 integer,
  tempo_vermelho_intermitente   integer,
  tempo_vermelho_limpeza        integer,
  tempo_atraso_grupo            integer,
  constraint uq_transicao_origem_id unique (origem_id),
  constraint uq_transicao_destino_id unique (destino_id),
  constraint pk_transicao primary key (id)
);

create table transicao_proibida (
  id                            uuid not null,
  origem_id                     uuid,
  destino_id                    uuid,
  alternativo_id                uuid,
  constraint uq_transicao_proibida_origem_id unique (origem_id),
  constraint uq_transicao_proibida_destino_id unique (destino_id),
  constraint uq_transicao_proibida_alternativo_id unique (alternativo_id),
  constraint pk_transicao_proibida primary key (id)
);

create table usuarios (
  login                         varchar(255) not null,
  senha                         varchar(255),
  email                         varchar(255),
  nome                          varchar(255),
  root                          boolean,
  area_id                       uuid,
  perfil_id                     uuid,
  data_criacao                  timestamp not null,
  data_atualizacao              timestamp not null,
  constraint pk_usuarios primary key (login)
);

alter table aneis add constraint fk_aneis_controlador_id foreign key (controlador_id) references controladores (id) on delete restrict on update restrict;
create index ix_aneis_controlador_id on aneis (controlador_id);

alter table areas add constraint fk_areas_cidade_id foreign key (cidade_id) references cidades (id) on delete restrict on update restrict;
create index ix_areas_cidade_id on areas (cidade_id);

alter table controladores add constraint fk_controladores_modelo_id foreign key (modelo_id) references modelo_controladores (id) on delete restrict on update restrict;
create index ix_controladores_modelo_id on controladores (modelo_id);

alter table controladores add constraint fk_controladores_area_id foreign key (area_id) references areas (id) on delete restrict on update restrict;
create index ix_controladores_area_id on controladores (area_id);

alter table detectores add constraint fk_detectores_anel_id foreign key (anel_id) references aneis (id) on delete restrict on update restrict;
create index ix_detectores_anel_id on detectores (anel_id);

alter table detectores add constraint fk_detectores_controlador_id foreign key (controlador_id) references controladores (id) on delete restrict on update restrict;
create index ix_detectores_controlador_id on detectores (controlador_id);

alter table estagios add constraint fk_estagios_imagem_id foreign key (imagem_id) references imagens (id) on delete restrict on update restrict;

alter table estagios add constraint fk_estagios_anel_id foreign key (anel_id) references aneis (id) on delete restrict on update restrict;
create index ix_estagios_anel_id on estagios (anel_id);

alter table estagios add constraint fk_estagios_controlador_id foreign key (controlador_id) references controladores (id) on delete restrict on update restrict;
create index ix_estagios_controlador_id on estagios (controlador_id);

alter table estagios_grupos_semaforicos add constraint fk_estagios_grupos_semaforicos_estagio_id foreign key (estagio_id) references estagios (id) on delete restrict on update restrict;
create index ix_estagios_grupos_semaforicos_estagio_id on estagios_grupos_semaforicos (estagio_id);

alter table estagios_grupos_semaforicos add constraint fk_estagios_grupos_semaforicos_grupo_semaforico_id foreign key (grupo_semaforico_id) references grupos_semaforicos (id) on delete restrict on update restrict;
create index ix_estagios_grupos_semaforicos_grupo_semaforico_id on estagios_grupos_semaforicos (grupo_semaforico_id);

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

alter table perfis_permissoes add constraint fk_perfis_permissoes_perfis foreign key (perfil_id) references perfis (id) on delete restrict on update restrict;
create index ix_perfis_permissoes_perfis on perfis_permissoes (perfil_id);

alter table perfis_permissoes add constraint fk_perfis_permissoes_permissoes foreign key (permissao_id) references permissoes (id) on delete restrict on update restrict;
create index ix_perfis_permissoes_permissoes on perfis_permissoes (permissao_id);

alter table permissoes_perfis add constraint fk_permissoes_perfis_permissoes foreign key (permissoes_id) references permissoes (id) on delete restrict on update restrict;
create index ix_permissoes_perfis_permissoes on permissoes_perfis (permissoes_id);

alter table permissoes_perfis add constraint fk_permissoes_perfis_perfis foreign key (perfis_id) references perfis (id) on delete restrict on update restrict;
create index ix_permissoes_perfis_perfis on permissoes_perfis (perfis_id);

alter table sessoes add constraint fk_sessoes_usuario_login foreign key (usuario_login) references usuarios (login) on delete restrict on update restrict;
create index ix_sessoes_usuario_login on sessoes (usuario_login);

alter table tabela_entre_verdes add constraint fk_tabela_entre_verdes_grupo_semaforico_id foreign key (grupo_semaforico_id) references grupos_semaforicos (id) on delete restrict on update restrict;
create index ix_tabela_entre_verdes_grupo_semaforico_id on tabela_entre_verdes (grupo_semaforico_id);

alter table transicao add constraint fk_transicao_tabela_entre_verdes_id foreign key (tabela_entre_verdes_id) references tabela_entre_verdes (id) on delete restrict on update restrict;
create index ix_transicao_tabela_entre_verdes_id on transicao (tabela_entre_verdes_id);

alter table transicao add constraint fk_transicao_origem_id foreign key (origem_id) references estagios (id) on delete restrict on update restrict;

alter table transicao add constraint fk_transicao_destino_id foreign key (destino_id) references estagios (id) on delete restrict on update restrict;

alter table transicao_proibida add constraint fk_transicao_proibida_origem_id foreign key (origem_id) references estagios (id) on delete restrict on update restrict;

alter table transicao_proibida add constraint fk_transicao_proibida_destino_id foreign key (destino_id) references estagios (id) on delete restrict on update restrict;

alter table transicao_proibida add constraint fk_transicao_proibida_alternativo_id foreign key (alternativo_id) references estagios (id) on delete restrict on update restrict;

alter table usuarios add constraint fk_usuarios_area_id foreign key (area_id) references areas (id) on delete restrict on update restrict;
create index ix_usuarios_area_id on usuarios (area_id);

alter table usuarios add constraint fk_usuarios_perfil_id foreign key (perfil_id) references perfis (id) on delete restrict on update restrict;
create index ix_usuarios_perfil_id on usuarios (perfil_id);


# --- !Downs

alter table aneis drop constraint if exists fk_aneis_controlador_id;
drop index if exists ix_aneis_controlador_id;

alter table areas drop constraint if exists fk_areas_cidade_id;
drop index if exists ix_areas_cidade_id;

alter table controladores drop constraint if exists fk_controladores_modelo_id;
drop index if exists ix_controladores_modelo_id;

alter table controladores drop constraint if exists fk_controladores_area_id;
drop index if exists ix_controladores_area_id;

alter table detectores drop constraint if exists fk_detectores_anel_id;
drop index if exists ix_detectores_anel_id;

alter table detectores drop constraint if exists fk_detectores_controlador_id;
drop index if exists ix_detectores_controlador_id;

alter table estagios drop constraint if exists fk_estagios_imagem_id;

alter table estagios drop constraint if exists fk_estagios_anel_id;
drop index if exists ix_estagios_anel_id;

alter table estagios drop constraint if exists fk_estagios_controlador_id;
drop index if exists ix_estagios_controlador_id;

alter table estagios_grupos_semaforicos drop constraint if exists fk_estagios_grupos_semaforicos_estagio_id;
drop index if exists ix_estagios_grupos_semaforicos_estagio_id;

alter table estagios_grupos_semaforicos drop constraint if exists fk_estagios_grupos_semaforicos_grupo_semaforico_id;
drop index if exists ix_estagios_grupos_semaforicos_grupo_semaforico_id;

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

alter table perfis_permissoes drop constraint if exists fk_perfis_permissoes_perfis;
drop index if exists ix_perfis_permissoes_perfis;

alter table perfis_permissoes drop constraint if exists fk_perfis_permissoes_permissoes;
drop index if exists ix_perfis_permissoes_permissoes;

alter table permissoes_perfis drop constraint if exists fk_permissoes_perfis_permissoes;
drop index if exists ix_permissoes_perfis_permissoes;

alter table permissoes_perfis drop constraint if exists fk_permissoes_perfis_perfis;
drop index if exists ix_permissoes_perfis_perfis;

alter table sessoes drop constraint if exists fk_sessoes_usuario_login;
drop index if exists ix_sessoes_usuario_login;

alter table tabela_entre_verdes drop constraint if exists fk_tabela_entre_verdes_grupo_semaforico_id;
drop index if exists ix_tabela_entre_verdes_grupo_semaforico_id;

alter table transicao drop constraint if exists fk_transicao_tabela_entre_verdes_id;
drop index if exists ix_transicao_tabela_entre_verdes_id;

alter table transicao drop constraint if exists fk_transicao_origem_id;

alter table transicao drop constraint if exists fk_transicao_destino_id;

alter table transicao_proibida drop constraint if exists fk_transicao_proibida_origem_id;

alter table transicao_proibida drop constraint if exists fk_transicao_proibida_destino_id;

alter table transicao_proibida drop constraint if exists fk_transicao_proibida_alternativo_id;

alter table usuarios drop constraint if exists fk_usuarios_area_id;
drop index if exists ix_usuarios_area_id;

alter table usuarios drop constraint if exists fk_usuarios_perfil_id;
drop index if exists ix_usuarios_perfil_id;

drop table if exists aneis;

drop table if exists areas;

drop table if exists cidades;

drop table if exists configuracao_controladores;

drop table if exists controladores;

drop table if exists detectores;

drop table if exists estagios;

drop table if exists estagios_grupos_semaforicos;

drop table if exists fabricantes;

drop table if exists grupos_semaforicos;

drop table if exists imagens;

drop table if exists limite_area;

drop table if exists modelo_controladores;

drop table if exists perfis;

drop table if exists perfis_permissoes;

drop table if exists permissoes;

drop table if exists permissoes_perfis;

drop table if exists sessoes;

drop table if exists tabela_entre_verdes;

drop table if exists transicao;

drop table if exists transicao_proibida;

drop table if exists usuarios;

