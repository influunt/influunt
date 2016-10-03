# --- !Ups

ALTER TABLE `permissoes`
CHANGE COLUMN `descricao` `descricao` VARCHAR(255) NOT NULL ,
CHANGE COLUMN `chave` `chave` VARCHAR(255) NOT NULL ;

create table permissoes_app (
  id                            varchar(40) not null,
  chave                         varchar(255) not null,
  nome                          varchar(255) not null,
  data_criacao                  datetime(6) not null,
  data_atualizacao              datetime(6) not null,
  constraint pk_permissoes_app primary key (id)
);

create table permissoes_app_permissoes (
  permissao_app_id              varchar(40) not null,
  permissao_id                  varchar(40) not null,
  constraint pk_permissoes_app_permissoes primary key (permissao_app_id,permissao_id)
);

alter table permissoes_app_permissoes add constraint fk_permissoes_app_permissoes_permissoes_app foreign key (permissao_app_id) references permissoes_app (id) on delete restrict on update restrict;
create index ix_permissoes_app_permissoes_permissoes_app on permissoes_app_permissoes (permissao_app_id);

alter table permissoes_app_permissoes add constraint fk_permissoes_app_permissoes_permissoes foreign key (permissao_id) references permissoes (id) on delete restrict on update restrict;
create index ix_permissoes_app_permissoes_permissoes on permissoes_app_permissoes (permissao_id);


# --- !Downs

alter table permissoes_app_permissoes drop foreign key fk_permissoes_app_permissoes_permissoes_app;
drop index ix_permissoes_app_permissoes_permissoes_app on permissoes_app_permissoes;

alter table permissoes_app_permissoes drop foreign key fk_permissoes_app_permissoes_permissoes;
drop index ix_permissoes_app_permissoes_permissoes on permissoes_app_permissoes;

drop table if exists permissoes_app;

drop table if exists permissoes_app_permissoes;

ALTER TABLE `permissoes`
CHANGE COLUMN `descricao` `descricao` VARCHAR(255) NULL ,
CHANGE COLUMN `chave` `chave` VARCHAR(255) NULL ;
