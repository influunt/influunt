# --- !Ups

alter table agrupamentos add column descricao varchar (255) after tipo;
alter table agrupamentos add column posicao_plano integer not null AFTER descricao;
alter table agrupamentos add column dia_da_semana varchar(16) not null after posicao_plano;
alter table agrupamentos add column horario time not null after dia_da_semana;

alter table agrupamentos change column nome nome varchar(255) not null;
alter table agrupamentos change column tipo tipo varchar(8) not null;


alter table agrupamentos_controladores drop foreign key fk_agrupamentos_controladores_agrupamentos;
drop index ix_agrupamentos_controladores_agrupamentos on agrupamentos_controladores;
alter table agrupamentos_controladores drop foreign key fk_agrupamentos_controladores_controladores;
drop index ix_agrupamentos_controladores_controladores on agrupamentos_controladores;
drop table if exists agrupamentos_controladores;


alter table planos drop foreign key fk_planos_agrupamento_id;
drop index ix_planos_agrupamento_id on planos;
alter table planos drop column agrupamento_id;


create table agrupamentos_aneis (
  agrupamento_id                varchar(40) not null,
  anel_id                       varchar(40) not null,
  constraint pk_agrupamentos_aneis primary key (agrupamento_id,anel_id)
);
alter table agrupamentos_aneis add constraint fk_agrupamentos_aneis_agrupamentos foreign key (agrupamento_id) references agrupamentos (id) on delete restrict on update restrict;
create index ix_agrupamentos_aneis_agrupamentos on agrupamentos_aneis (agrupamento_id);
alter table agrupamentos_aneis add constraint fk_agrupamentos_aneis_aneis foreign key (anel_id) references aneis (id) on delete restrict on update restrict;
create index ix_agrupamentos_aneis_aneis on agrupamentos_aneis (anel_id);



# --- !Downs

alter table agrupamentos_aneis drop foreign key fk_agrupamentos_aneis_agrupamentos;
drop index ix_agrupamentos_aneis_agrupamentos on agrupamentos_aneis;
alter table agrupamentos_aneis drop foreign key fk_agrupamentos_aneis_aneis;
drop index ix_agrupamentos_aneis_aneis on agrupamentos_aneis;
drop table if exists agrupamentos_aneis;


alter table planos add column agrupamento_id varchar(40) after versao_plano_id;
alter table planos add constraint fk_planos_agrupamento_id foreign key (agrupamento_id) references agrupamentos (id) on delete restrict on update restrict;
create index ix_planos_agrupamento_id on planos (agrupamento_id);


create table agrupamentos_controladores (
  agrupamento_id                varchar(40) not null,
  controlador_id                varchar(40) not null,
  constraint pk_agrupamentos_controladores primary key (agrupamento_id,controlador_id)
);
alter table agrupamentos_controladores add constraint fk_agrupamentos_controladores_agrupamentos foreign key (agrupamento_id) references agrupamentos (id) on delete restrict on update restrict;
create index ix_agrupamentos_controladores_agrupamentos on agrupamentos_controladores (agrupamento_id);
alter table agrupamentos_controladores add constraint fk_agrupamentos_controladores_controladores foreign key (controlador_id) references controladores (id) on delete restrict on update restrict;
create index ix_agrupamentos_controladores_controladores on agrupamentos_controladores (controlador_id);


alter table agrupamentos drop column horario;
alter table agrupamentos drop column dia_da_semana;
alter table agrupamentos drop column posicao_plano;
alter table agrupamentos drop column descricao;
