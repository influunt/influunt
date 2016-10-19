# --- !Ups

alter table intervalos drop foreign key fk_intervalos_grupo_semaforico_plano_id;
drop index ix_intervalos_grupo_semaforico_plano_id on intervalos;

drop table if exists intervalos;


# --- !Downs

create table intervalos (
  id                            varchar(40) not null,
  id_json                       varchar(255),
  grupo_semaforico_plano_id     varchar(40) not null,
  ordem                         integer not null,
  estado_grupo_semaforico       varchar(21) not null,
  tamanho                       integer not null,
  constraint ck_intervalos_estado_grupo_semaforico check (estado_grupo_semaforico in ('APAGADO','VERDE','AMARELO','VERMELHO','VERMELHO_INTERMITENTE','INTERMITENTE','VERMELHO_LIMPEZA')),
  constraint pk_intervalos primary key (id)
);

alter table intervalos add constraint fk_intervalos_grupo_semaforico_plano_id foreign key (grupo_semaforico_plano_id) references grupos_semaforicos_planos (id) on delete restrict on update restrict;
create index ix_intervalos_grupo_semaforico_plano_id on intervalos (grupo_semaforico_plano_id);
