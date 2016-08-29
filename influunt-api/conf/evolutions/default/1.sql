# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table agrupamentos (
  id                            uuid not null,
  id_json                       varchar(255),
  nome                          varchar(255),
  numero                        varchar(255),
  tipo                          varchar(8),
  data_criacao                  timestamp not null,
  data_atualizacao              timestamp not null,
  constraint ck_agrupamentos_tipo check (tipo in ('ROTA','CORREDOR')),
  constraint pk_agrupamentos primary key (id)
);

create table agrupamentos_controladores (
  agrupamento_id                uuid not null,
  controlador_id                uuid not null,
  constraint pk_agrupamentos_controladores primary key (agrupamento_id,controlador_id)
);

create table aneis (
  id                            uuid not null,
  id_json                       varchar(255),
  ativo                         boolean not null,
  descricao                     varchar(255),
  posicao                       integer,
  numero_smee                   varchar(255),
  controlador_id                uuid,
  croqui_id                     uuid,
  data_criacao                  timestamp not null,
  data_atualizacao              timestamp not null,
  constraint uq_aneis_croqui_id unique (croqui_id),
  constraint pk_aneis primary key (id)
);

create table areas (
  id                            uuid not null,
  id_json                       varchar(255),
  descricao                     integer not null,
  cidade_id                     uuid not null,
  data_criacao                  timestamp not null,
  data_atualizacao              timestamp not null,
  constraint pk_areas primary key (id)
);

create table atrasos_de_grupos (
  id                            uuid not null,
  id_json                       varchar(255),
  transicao_id                  uuid,
  atraso_de_grupo               integer not null,
  data_criacao                  timestamp not null,
  data_atualizacao              timestamp not null,
  constraint uq_atrasos_de_grupos_transicao_id unique (transicao_id),
  constraint pk_atrasos_de_grupos primary key (id)
);

create table cidades (
  id                            uuid not null,
  id_json                       varchar(255),
  nome                          varchar(255),
  data_criacao                  timestamp not null,
  data_atualizacao              timestamp not null,
  constraint pk_cidades primary key (id)
);

create table controladores (
  id                            uuid not null,
  id_json                       varchar(255),
  nome_endereco                 varchar(255) not null,
  status_controlador            integer,
  sequencia                     integer,
  numero_smee                   varchar(255),
  numero_smeeconjugado1         varchar(255),
  numero_smeeconjugado2         varchar(255),
  numero_smeeconjugado3         varchar(255),
  firmware                      varchar(255),
  modelo_id                     uuid not null,
  area_id                       uuid not null,
  subarea_id                    uuid,
  data_criacao                  timestamp not null,
  data_atualizacao              timestamp not null,
  constraint ck_controladores_status_controlador check (status_controlador in (0,1,2,3,4)),
  constraint pk_controladores primary key (id)
);

create table detectores (
  id                            uuid not null,
  id_json                       varchar(255),
  tipo                          varchar(8),
  anel_id                       uuid,
  estagio_id                    uuid,
  controlador_id                uuid,
  posicao                       integer,
  descricao                     varchar(255),
  monitorado                    boolean,
  tempo_ausencia_deteccao       integer,
  tempo_deteccao_permanente     integer,
  data_criacao                  timestamp not null,
  data_atualizacao              timestamp not null,
  constraint ck_detectores_tipo check (tipo in ('VEICULAR','PEDESTRE')),
  constraint uq_detectores_estagio_id unique (estagio_id),
  constraint pk_detectores primary key (id)
);

create table enderecos (
  id                            uuid not null,
  id_json                       varchar(255),
  controlador_id                uuid,
  anel_id                       uuid,
  localizacao                   varchar(255),
  latitude                      double not null,
  longitude                     double not null,
  localizacao2                  varchar(255),
  altura_numerica               integer,
  referencia                    varchar(255),
  data_criacao                  timestamp not null,
  data_atualizacao              timestamp not null,
  constraint uq_enderecos_controlador_id unique (controlador_id),
  constraint uq_enderecos_anel_id unique (anel_id),
  constraint pk_enderecos primary key (id)
);

create table estagios (
  id                            uuid not null,
  id_json                       varchar(255),
  imagem_id                     uuid,
  descricao                     varchar(255),
  tempo_maximo_permanencia      integer,
  tempo_maximo_permanencia_ativado boolean,
  posicao                       integer,
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
  id_json                       varchar(255),
  ativo                         boolean not null,
  estagio_id                    uuid not null,
  grupo_semaforico_id           uuid not null,
  data_criacao                  timestamp not null,
  data_atualizacao              timestamp not null,
  constraint pk_estagios_grupos_semaforicos primary key (id)
);

create table estagios_planos (
  id                            uuid not null,
  id_json                       varchar(255),
  estagio_id                    uuid not null,
  plano_id                      uuid not null,
  posicao                       integer,
  tempo_verde                   integer,
  tempo_verde_minimo            integer,
  tempo_verde_maximo            integer,
  tempo_verde_intermediario     integer,
  tempo_extensao_verde          double,
  dispensavel                   boolean,
  estagio_que_recebe_estagio_dispensavel_id uuid,
  data_criacao                  timestamp not null,
  data_atualizacao              timestamp not null,
  constraint pk_estagios_planos primary key (id)
);

create table eventos (
  id                            uuid not null,
  id_json                       varchar(255),
  posicao                       integer not null,
  dia_da_semana                 varchar(16),
  horario                       time not null,
  data                          timestamp,
  nome                          varchar(255),
  posicao_plano                 integer not null,
  tipo                          varchar(23) not null,
  tabela_horario_id             uuid not null,
  data_criacao                  timestamp not null,
  data_atualizacao              timestamp not null,
  constraint ck_eventos_dia_da_semana check (dia_da_semana in ('SEGUNDA_A_SEXTA','SEGUNDA_A_SABADO','SABADO_A_DOMINGO','TODOS_OS_DIAS','SEGUNDA','TERCA','QUARTA','QUINTA','SEXTA','SABADO','DOMINGO')),
  constraint ck_eventos_tipo check (tipo in ('NORMAL','ESPECIAL_RECORRENTE','ESPECIAL_NAO_RECORRENTE')),
  constraint pk_eventos primary key (id)
);

create table fabricantes (
  id                            uuid not null,
  id_json                       varchar(255),
  nome                          varchar(255) not null,
  data_criacao                  timestamp not null,
  data_atualizacao              timestamp not null,
  constraint pk_fabricantes primary key (id)
);

create table grupos_semaforicos (
  id                            uuid not null,
  id_json                       varchar(255),
  tipo                          varchar(8),
  descricao                     varchar(255),
  anel_id                       uuid,
  controlador_id                uuid,
  posicao                       integer,
  fase_vermelha_apagada_amarelo_intermitente boolean,
  tempo_verde_seguranca         integer,
  data_criacao                  timestamp not null,
  data_atualizacao              timestamp not null,
  constraint ck_grupos_semaforicos_tipo check (tipo in ('PEDESTRE','VEICULAR')),
  constraint pk_grupos_semaforicos primary key (id)
);

create table grupos_semaforicos_planos (
  id                            uuid not null,
  id_json                       varchar(255),
  grupo_semaforico_id           uuid not null,
  plano_id                      uuid not null,
  ativado                       boolean,
  data_criacao                  timestamp not null,
  data_atualizacao              timestamp not null,
  constraint pk_grupos_semaforicos_planos primary key (id)
);

create table imagens (
  id                            uuid not null,
  id_json                       varchar(255),
  filename                      varchar(255),
  content_type                  varchar(255),
  data_criacao                  timestamp not null,
  data_atualizacao              timestamp not null,
  constraint pk_imagens primary key (id)
);

create table limite_area (
  id                            uuid not null,
  id_json                       varchar(255),
  latitude                      double,
  longitude                     double,
  posicao                       integer,
  area_id                       uuid,
  data_criacao                  timestamp not null,
  data_atualizacao              timestamp not null,
  constraint pk_limite_area primary key (id)
);

create table modelo_controladores (
  id                            uuid not null,
  id_json                       varchar(255),
  fabricante_id                 uuid not null,
  descricao                     varchar(255) not null,
  limite_estagio                integer not null,
  limite_grupo_semaforico       integer not null,
  limite_anel                   integer not null,
  limite_detector_pedestre      integer not null,
  limite_detector_veicular      integer not null,
  limite_tabelas_entre_verdes   integer not null,
  limite_planos                 integer not null,
  data_criacao                  timestamp not null,
  data_atualizacao              timestamp not null,
  constraint pk_modelo_controladores primary key (id)
);

create table perfis (
  id                            uuid not null,
  id_json                       varchar(255),
  nome                          varchar(255),
  data_criacao                  timestamp not null,
  data_atualizacao              timestamp not null,
  constraint pk_perfis primary key (id)
);

create table permissoes_perfis (
  perfil_id                     uuid not null,
  permissao_id                  uuid not null,
  constraint pk_permissoes_perfis primary key (perfil_id,permissao_id)
);

create table permissoes (
  id                            uuid not null,
  id_json                       varchar(255),
  descricao                     varchar(255),
  chave                         varchar(255),
  data_criacao                  timestamp not null,
  data_atualizacao              timestamp not null,
  constraint pk_permissoes primary key (id)
);

create table planos (
  id                            uuid not null,
  id_json                       varchar(255),
  posicao                       integer not null,
  descricao                     varchar(255) not null,
  tempo_ciclo                   integer,
  defasagem                     integer,
  anel_id                       uuid,
  agrupamento_id                uuid,
  modo_operacao                 integer not null,
  posicao_tabela_entre_verde    integer not null,
  data_criacao                  timestamp not null,
  data_atualizacao              timestamp not null,
  constraint ck_planos_modo_operacao check (modo_operacao in (0,1,2,3,4)),
  constraint pk_planos primary key (id)
);

create table sessoes (
  id                            uuid not null,
  id_json                       varchar(255),
  usuario_id                    uuid,
  ativa                         boolean,
  data_criacao                  timestamp not null,
  constraint pk_sessoes primary key (id)
);

create table subareas (
  id                            uuid not null,
  id_json                       varchar(255),
  nome                          varchar(255),
  numero                        integer,
  area_id                       uuid not null,
  data_criacao                  timestamp not null,
  data_atualizacao              timestamp not null,
  constraint pk_subareas primary key (id)
);

create table tabela_entre_verdes (
  id                            uuid not null,
  id_json                       varchar(255),
  descricao                     varchar(255),
  grupo_semaforico_id           uuid,
  posicao                       integer,
  data_criacao                  timestamp not null,
  data_atualizacao              timestamp not null,
  constraint pk_tabela_entre_verdes primary key (id)
);

create table tabela_entre_verdes_transicao (
  id                            uuid not null,
  id_json                       varchar(255),
  tabela_entre_verdes_id        uuid,
  transicao_id                  uuid,
  tempo_amarelo                 integer,
  tempo_vermelho_intermitente   integer,
  tempo_vermelho_limpeza        integer not null,
  tempo_atraso_grupo            integer not null,
  data_criacao                  timestamp not null,
  data_atualizacao              timestamp not null,
  constraint pk_tabela_entre_verdes_transicao primary key (id)
);

create table tabela_horarios (
  id                            uuid not null,
  id_json                       varchar(255),
  controlador_id                uuid,
  data_criacao                  timestamp not null,
  data_atualizacao              timestamp not null,
  constraint uq_tabela_horarios_controlador_id unique (controlador_id),
  constraint pk_tabela_horarios primary key (id)
);

create table transicoes (
  id                            uuid not null,
  id_json                       varchar(255),
  tipo                          varchar(17) not null,
  grupo_semaforico_id           uuid,
  origem_id                     uuid,
  destino_id                    uuid,
  destroy                       boolean,
  data_criacao                  timestamp not null,
  data_atualizacao              timestamp not null,
  constraint ck_transicoes_tipo check (tipo in ('PERDA_DE_PASSAGEM','GANHO_DE_PASSAGEM')),
  constraint pk_transicoes primary key (id)
);

create table transicoes_proibidas (
  id                            uuid not null,
  id_json                       varchar(255),
  origem_id                     uuid not null,
  destino_id                    uuid not null,
  alternativo_id                uuid not null,
  data_criacao                  timestamp not null,
  data_atualizacao              timestamp not null,
  constraint pk_transicoes_proibidas primary key (id)
);

create table usuarios (
  id                            uuid not null,
  senha                         varchar(255),
  id_json                       varchar(255),
  login                         varchar(255),
  email                         varchar(255),
  nome                          varchar(255),
  root                          boolean,
  area_id                       uuid,
  perfil_id                     uuid,
  data_criacao                  timestamp not null,
  data_atualizacao              timestamp not null,
  constraint uq_usuarios_login unique (login),
  constraint pk_usuarios primary key (id)
);

create table verdes_conflitantes (
  id                            uuid not null,
  id_json                       varchar(255),
  origem_id                     uuid,
  destino_id                    uuid,
  data_criacao                  timestamp not null,
  data_atualizacao              timestamp not null,
  constraint pk_verdes_conflitantes primary key (id)
);

create table versoes_controladores (
  id                            uuid not null,
  controlador_origem_id         uuid,
  controlador_edicao_id         uuid,
  usuario_id                    uuid,
  descricao                     varchar(255),
  data_criacao                  timestamp not null,
  constraint pk_versoes_controladores primary key (id)
);

alter table agrupamentos_controladores add constraint fk_agrupamentos_controladores_agrupamentos foreign key (agrupamento_id) references agrupamentos (id) on delete restrict on update restrict;
create index ix_agrupamentos_controladores_agrupamentos on agrupamentos_controladores (agrupamento_id);

alter table agrupamentos_controladores add constraint fk_agrupamentos_controladores_controladores foreign key (controlador_id) references controladores (id) on delete restrict on update restrict;
create index ix_agrupamentos_controladores_controladores on agrupamentos_controladores (controlador_id);

alter table aneis add constraint fk_aneis_controlador_id foreign key (controlador_id) references controladores (id) on delete restrict on update restrict;
create index ix_aneis_controlador_id on aneis (controlador_id);

alter table aneis add constraint fk_aneis_croqui_id foreign key (croqui_id) references imagens (id) on delete restrict on update restrict;

alter table areas add constraint fk_areas_cidade_id foreign key (cidade_id) references cidades (id) on delete restrict on update restrict;
create index ix_areas_cidade_id on areas (cidade_id);

alter table atrasos_de_grupos add constraint fk_atrasos_de_grupos_transicao_id foreign key (transicao_id) references transicoes (id) on delete restrict on update restrict;

alter table controladores add constraint fk_controladores_modelo_id foreign key (modelo_id) references modelo_controladores (id) on delete restrict on update restrict;
create index ix_controladores_modelo_id on controladores (modelo_id);

alter table controladores add constraint fk_controladores_area_id foreign key (area_id) references areas (id) on delete restrict on update restrict;
create index ix_controladores_area_id on controladores (area_id);

alter table controladores add constraint fk_controladores_subarea_id foreign key (subarea_id) references subareas (id) on delete restrict on update restrict;
create index ix_controladores_subarea_id on controladores (subarea_id);

alter table detectores add constraint fk_detectores_anel_id foreign key (anel_id) references aneis (id) on delete restrict on update restrict;
create index ix_detectores_anel_id on detectores (anel_id);

alter table detectores add constraint fk_detectores_estagio_id foreign key (estagio_id) references estagios (id) on delete restrict on update restrict;

alter table detectores add constraint fk_detectores_controlador_id foreign key (controlador_id) references controladores (id) on delete restrict on update restrict;
create index ix_detectores_controlador_id on detectores (controlador_id);

alter table enderecos add constraint fk_enderecos_controlador_id foreign key (controlador_id) references controladores (id) on delete restrict on update restrict;

alter table enderecos add constraint fk_enderecos_anel_id foreign key (anel_id) references aneis (id) on delete restrict on update restrict;

alter table estagios add constraint fk_estagios_imagem_id foreign key (imagem_id) references imagens (id) on delete restrict on update restrict;

alter table estagios add constraint fk_estagios_anel_id foreign key (anel_id) references aneis (id) on delete restrict on update restrict;
create index ix_estagios_anel_id on estagios (anel_id);

alter table estagios add constraint fk_estagios_controlador_id foreign key (controlador_id) references controladores (id) on delete restrict on update restrict;
create index ix_estagios_controlador_id on estagios (controlador_id);

alter table estagios_grupos_semaforicos add constraint fk_estagios_grupos_semaforicos_estagio_id foreign key (estagio_id) references estagios (id) on delete restrict on update restrict;
create index ix_estagios_grupos_semaforicos_estagio_id on estagios_grupos_semaforicos (estagio_id);

alter table estagios_grupos_semaforicos add constraint fk_estagios_grupos_semaforicos_grupo_semaforico_id foreign key (grupo_semaforico_id) references grupos_semaforicos (id) on delete restrict on update restrict;
create index ix_estagios_grupos_semaforicos_grupo_semaforico_id on estagios_grupos_semaforicos (grupo_semaforico_id);

alter table estagios_planos add constraint fk_estagios_planos_estagio_id foreign key (estagio_id) references estagios (id) on delete restrict on update restrict;
create index ix_estagios_planos_estagio_id on estagios_planos (estagio_id);

alter table estagios_planos add constraint fk_estagios_planos_plano_id foreign key (plano_id) references planos (id) on delete restrict on update restrict;
create index ix_estagios_planos_plano_id on estagios_planos (plano_id);

alter table estagios_planos add constraint fk_estagios_planos_estagio_que_recebe_estagio_dispensavel_3 foreign key (estagio_que_recebe_estagio_dispensavel_id) references estagios_planos (id) on delete restrict on update restrict;
create index ix_estagios_planos_estagio_que_recebe_estagio_dispensavel_3 on estagios_planos (estagio_que_recebe_estagio_dispensavel_id);

alter table eventos add constraint fk_eventos_tabela_horario_id foreign key (tabela_horario_id) references tabela_horarios (id) on delete restrict on update restrict;
create index ix_eventos_tabela_horario_id on eventos (tabela_horario_id);

alter table grupos_semaforicos add constraint fk_grupos_semaforicos_anel_id foreign key (anel_id) references aneis (id) on delete restrict on update restrict;
create index ix_grupos_semaforicos_anel_id on grupos_semaforicos (anel_id);

alter table grupos_semaforicos add constraint fk_grupos_semaforicos_controlador_id foreign key (controlador_id) references controladores (id) on delete restrict on update restrict;
create index ix_grupos_semaforicos_controlador_id on grupos_semaforicos (controlador_id);

alter table grupos_semaforicos_planos add constraint fk_grupos_semaforicos_planos_grupo_semaforico_id foreign key (grupo_semaforico_id) references grupos_semaforicos (id) on delete restrict on update restrict;
create index ix_grupos_semaforicos_planos_grupo_semaforico_id on grupos_semaforicos_planos (grupo_semaforico_id);

alter table grupos_semaforicos_planos add constraint fk_grupos_semaforicos_planos_plano_id foreign key (plano_id) references planos (id) on delete restrict on update restrict;
create index ix_grupos_semaforicos_planos_plano_id on grupos_semaforicos_planos (plano_id);

alter table limite_area add constraint fk_limite_area_area_id foreign key (area_id) references areas (id) on delete restrict on update restrict;
create index ix_limite_area_area_id on limite_area (area_id);

alter table modelo_controladores add constraint fk_modelo_controladores_fabricante_id foreign key (fabricante_id) references fabricantes (id) on delete restrict on update restrict;
create index ix_modelo_controladores_fabricante_id on modelo_controladores (fabricante_id);

alter table permissoes_perfis add constraint fk_permissoes_perfis_perfis foreign key (perfil_id) references perfis (id) on delete restrict on update restrict;
create index ix_permissoes_perfis_perfis on permissoes_perfis (perfil_id);

alter table permissoes_perfis add constraint fk_permissoes_perfis_permissoes foreign key (permissao_id) references permissoes (id) on delete restrict on update restrict;
create index ix_permissoes_perfis_permissoes on permissoes_perfis (permissao_id);

alter table planos add constraint fk_planos_anel_id foreign key (anel_id) references aneis (id) on delete restrict on update restrict;
create index ix_planos_anel_id on planos (anel_id);

alter table planos add constraint fk_planos_agrupamento_id foreign key (agrupamento_id) references agrupamentos (id) on delete restrict on update restrict;
create index ix_planos_agrupamento_id on planos (agrupamento_id);

alter table sessoes add constraint fk_sessoes_usuario_id foreign key (usuario_id) references usuarios (id) on delete restrict on update restrict;
create index ix_sessoes_usuario_id on sessoes (usuario_id);

alter table subareas add constraint fk_subareas_area_id foreign key (area_id) references areas (id) on delete restrict on update restrict;
create index ix_subareas_area_id on subareas (area_id);

alter table tabela_entre_verdes add constraint fk_tabela_entre_verdes_grupo_semaforico_id foreign key (grupo_semaforico_id) references grupos_semaforicos (id) on delete restrict on update restrict;
create index ix_tabela_entre_verdes_grupo_semaforico_id on tabela_entre_verdes (grupo_semaforico_id);

alter table tabela_entre_verdes_transicao add constraint fk_tabela_entre_verdes_transicao_tabela_entre_verdes_id foreign key (tabela_entre_verdes_id) references tabela_entre_verdes (id) on delete restrict on update restrict;
create index ix_tabela_entre_verdes_transicao_tabela_entre_verdes_id on tabela_entre_verdes_transicao (tabela_entre_verdes_id);

alter table tabela_entre_verdes_transicao add constraint fk_tabela_entre_verdes_transicao_transicao_id foreign key (transicao_id) references transicoes (id) on delete restrict on update restrict;
create index ix_tabela_entre_verdes_transicao_transicao_id on tabela_entre_verdes_transicao (transicao_id);

alter table tabela_horarios add constraint fk_tabela_horarios_controlador_id foreign key (controlador_id) references controladores (id) on delete restrict on update restrict;

alter table transicoes add constraint fk_transicoes_grupo_semaforico_id foreign key (grupo_semaforico_id) references grupos_semaforicos (id) on delete restrict on update restrict;
create index ix_transicoes_grupo_semaforico_id on transicoes (grupo_semaforico_id);

alter table transicoes add constraint fk_transicoes_origem_id foreign key (origem_id) references estagios (id) on delete restrict on update restrict;
create index ix_transicoes_origem_id on transicoes (origem_id);

alter table transicoes add constraint fk_transicoes_destino_id foreign key (destino_id) references estagios (id) on delete restrict on update restrict;
create index ix_transicoes_destino_id on transicoes (destino_id);

alter table transicoes_proibidas add constraint fk_transicoes_proibidas_origem_id foreign key (origem_id) references estagios (id) on delete restrict on update restrict;
create index ix_transicoes_proibidas_origem_id on transicoes_proibidas (origem_id);

alter table transicoes_proibidas add constraint fk_transicoes_proibidas_destino_id foreign key (destino_id) references estagios (id) on delete restrict on update restrict;
create index ix_transicoes_proibidas_destino_id on transicoes_proibidas (destino_id);

alter table transicoes_proibidas add constraint fk_transicoes_proibidas_alternativo_id foreign key (alternativo_id) references estagios (id) on delete restrict on update restrict;
create index ix_transicoes_proibidas_alternativo_id on transicoes_proibidas (alternativo_id);

alter table usuarios add constraint fk_usuarios_area_id foreign key (area_id) references areas (id) on delete restrict on update restrict;
create index ix_usuarios_area_id on usuarios (area_id);

alter table usuarios add constraint fk_usuarios_perfil_id foreign key (perfil_id) references perfis (id) on delete restrict on update restrict;
create index ix_usuarios_perfil_id on usuarios (perfil_id);

alter table verdes_conflitantes add constraint fk_verdes_conflitantes_origem_id foreign key (origem_id) references grupos_semaforicos (id) on delete restrict on update restrict;
create index ix_verdes_conflitantes_origem_id on verdes_conflitantes (origem_id);

alter table verdes_conflitantes add constraint fk_verdes_conflitantes_destino_id foreign key (destino_id) references grupos_semaforicos (id) on delete restrict on update restrict;
create index ix_verdes_conflitantes_destino_id on verdes_conflitantes (destino_id);

alter table versoes_controladores add constraint fk_versoes_controladores_controlador_origem_id foreign key (controlador_origem_id) references controladores (id) on delete restrict on update restrict;
create index ix_versoes_controladores_controlador_origem_id on versoes_controladores (controlador_origem_id);

alter table versoes_controladores add constraint fk_versoes_controladores_controlador_edicao_id foreign key (controlador_edicao_id) references controladores (id) on delete restrict on update restrict;
create index ix_versoes_controladores_controlador_edicao_id on versoes_controladores (controlador_edicao_id);

alter table versoes_controladores add constraint fk_versoes_controladores_usuario_id foreign key (usuario_id) references usuarios (id) on delete restrict on update restrict;
create index ix_versoes_controladores_usuario_id on versoes_controladores (usuario_id);


# --- !Downs

alter table agrupamentos_controladores drop constraint if exists fk_agrupamentos_controladores_agrupamentos;
drop index if exists ix_agrupamentos_controladores_agrupamentos;

alter table agrupamentos_controladores drop constraint if exists fk_agrupamentos_controladores_controladores;
drop index if exists ix_agrupamentos_controladores_controladores;

alter table aneis drop constraint if exists fk_aneis_controlador_id;
drop index if exists ix_aneis_controlador_id;

alter table aneis drop constraint if exists fk_aneis_croqui_id;

alter table areas drop constraint if exists fk_areas_cidade_id;
drop index if exists ix_areas_cidade_id;

alter table atrasos_de_grupos drop constraint if exists fk_atrasos_de_grupos_transicao_id;

alter table controladores drop constraint if exists fk_controladores_modelo_id;
drop index if exists ix_controladores_modelo_id;

alter table controladores drop constraint if exists fk_controladores_area_id;
drop index if exists ix_controladores_area_id;

alter table controladores drop constraint if exists fk_controladores_subarea_id;
drop index if exists ix_controladores_subarea_id;

alter table detectores drop constraint if exists fk_detectores_anel_id;
drop index if exists ix_detectores_anel_id;

alter table detectores drop constraint if exists fk_detectores_estagio_id;

alter table detectores drop constraint if exists fk_detectores_controlador_id;
drop index if exists ix_detectores_controlador_id;

alter table enderecos drop constraint if exists fk_enderecos_controlador_id;

alter table enderecos drop constraint if exists fk_enderecos_anel_id;

alter table estagios drop constraint if exists fk_estagios_imagem_id;

alter table estagios drop constraint if exists fk_estagios_anel_id;
drop index if exists ix_estagios_anel_id;

alter table estagios drop constraint if exists fk_estagios_controlador_id;
drop index if exists ix_estagios_controlador_id;

alter table estagios_grupos_semaforicos drop constraint if exists fk_estagios_grupos_semaforicos_estagio_id;
drop index if exists ix_estagios_grupos_semaforicos_estagio_id;

alter table estagios_grupos_semaforicos drop constraint if exists fk_estagios_grupos_semaforicos_grupo_semaforico_id;
drop index if exists ix_estagios_grupos_semaforicos_grupo_semaforico_id;

alter table estagios_planos drop constraint if exists fk_estagios_planos_estagio_id;
drop index if exists ix_estagios_planos_estagio_id;

alter table estagios_planos drop constraint if exists fk_estagios_planos_plano_id;
drop index if exists ix_estagios_planos_plano_id;

alter table estagios_planos drop constraint if exists fk_estagios_planos_estagio_que_recebe_estagio_dispensavel_3;
drop index if exists ix_estagios_planos_estagio_que_recebe_estagio_dispensavel_3;

alter table eventos drop constraint if exists fk_eventos_tabela_horario_id;
drop index if exists ix_eventos_tabela_horario_id;

alter table grupos_semaforicos drop constraint if exists fk_grupos_semaforicos_anel_id;
drop index if exists ix_grupos_semaforicos_anel_id;

alter table grupos_semaforicos drop constraint if exists fk_grupos_semaforicos_controlador_id;
drop index if exists ix_grupos_semaforicos_controlador_id;

alter table grupos_semaforicos_planos drop constraint if exists fk_grupos_semaforicos_planos_grupo_semaforico_id;
drop index if exists ix_grupos_semaforicos_planos_grupo_semaforico_id;

alter table grupos_semaforicos_planos drop constraint if exists fk_grupos_semaforicos_planos_plano_id;
drop index if exists ix_grupos_semaforicos_planos_plano_id;

alter table limite_area drop constraint if exists fk_limite_area_area_id;
drop index if exists ix_limite_area_area_id;

alter table modelo_controladores drop constraint if exists fk_modelo_controladores_fabricante_id;
drop index if exists ix_modelo_controladores_fabricante_id;

alter table permissoes_perfis drop constraint if exists fk_permissoes_perfis_perfis;
drop index if exists ix_permissoes_perfis_perfis;

alter table permissoes_perfis drop constraint if exists fk_permissoes_perfis_permissoes;
drop index if exists ix_permissoes_perfis_permissoes;

alter table planos drop constraint if exists fk_planos_anel_id;
drop index if exists ix_planos_anel_id;

alter table planos drop constraint if exists fk_planos_agrupamento_id;
drop index if exists ix_planos_agrupamento_id;

alter table sessoes drop constraint if exists fk_sessoes_usuario_id;
drop index if exists ix_sessoes_usuario_id;

alter table subareas drop constraint if exists fk_subareas_area_id;
drop index if exists ix_subareas_area_id;

alter table tabela_entre_verdes drop constraint if exists fk_tabela_entre_verdes_grupo_semaforico_id;
drop index if exists ix_tabela_entre_verdes_grupo_semaforico_id;

alter table tabela_entre_verdes_transicao drop constraint if exists fk_tabela_entre_verdes_transicao_tabela_entre_verdes_id;
drop index if exists ix_tabela_entre_verdes_transicao_tabela_entre_verdes_id;

alter table tabela_entre_verdes_transicao drop constraint if exists fk_tabela_entre_verdes_transicao_transicao_id;
drop index if exists ix_tabela_entre_verdes_transicao_transicao_id;

alter table tabela_horarios drop constraint if exists fk_tabela_horarios_controlador_id;

alter table transicoes drop constraint if exists fk_transicoes_grupo_semaforico_id;
drop index if exists ix_transicoes_grupo_semaforico_id;

alter table transicoes drop constraint if exists fk_transicoes_origem_id;
drop index if exists ix_transicoes_origem_id;

alter table transicoes drop constraint if exists fk_transicoes_destino_id;
drop index if exists ix_transicoes_destino_id;

alter table transicoes_proibidas drop constraint if exists fk_transicoes_proibidas_origem_id;
drop index if exists ix_transicoes_proibidas_origem_id;

alter table transicoes_proibidas drop constraint if exists fk_transicoes_proibidas_destino_id;
drop index if exists ix_transicoes_proibidas_destino_id;

alter table transicoes_proibidas drop constraint if exists fk_transicoes_proibidas_alternativo_id;
drop index if exists ix_transicoes_proibidas_alternativo_id;

alter table usuarios drop constraint if exists fk_usuarios_area_id;
drop index if exists ix_usuarios_area_id;

alter table usuarios drop constraint if exists fk_usuarios_perfil_id;
drop index if exists ix_usuarios_perfil_id;

alter table verdes_conflitantes drop constraint if exists fk_verdes_conflitantes_origem_id;
drop index if exists ix_verdes_conflitantes_origem_id;

alter table verdes_conflitantes drop constraint if exists fk_verdes_conflitantes_destino_id;
drop index if exists ix_verdes_conflitantes_destino_id;

alter table versoes_controladores drop constraint if exists fk_versoes_controladores_controlador_origem_id;
drop index if exists ix_versoes_controladores_controlador_origem_id;

alter table versoes_controladores drop constraint if exists fk_versoes_controladores_controlador_edicao_id;
drop index if exists ix_versoes_controladores_controlador_edicao_id;

alter table versoes_controladores drop constraint if exists fk_versoes_controladores_usuario_id;
drop index if exists ix_versoes_controladores_usuario_id;

drop table if exists agrupamentos;

drop table if exists agrupamentos_controladores;

drop table if exists aneis;

drop table if exists areas;

drop table if exists atrasos_de_grupos;

drop table if exists cidades;

drop table if exists controladores;

drop table if exists detectores;

drop table if exists enderecos;

drop table if exists estagios;

drop table if exists estagios_grupos_semaforicos;

drop table if exists estagios_planos;

drop table if exists eventos;

drop table if exists fabricantes;

drop table if exists grupos_semaforicos;

drop table if exists grupos_semaforicos_planos;

drop table if exists imagens;

drop table if exists limite_area;

drop table if exists modelo_controladores;

drop table if exists perfis;

drop table if exists permissoes_perfis;

drop table if exists permissoes;

drop table if exists planos;

drop table if exists sessoes;

drop table if exists subareas;

drop table if exists tabela_entre_verdes;

drop table if exists tabela_entre_verdes_transicao;

drop table if exists tabela_horarios;

drop table if exists transicoes;

drop table if exists transicoes_proibidas;

drop table if exists usuarios;

drop table if exists verdes_conflitantes;

drop table if exists versoes_controladores;

