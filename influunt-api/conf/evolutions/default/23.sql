# --- !Ups

create table disparos_alarmes (
  id                            varchar(40) not null,
  id_json                       varchar(255),
  chave                         varchar(255),
  usuario_id                    varchar(40),
  data_criacao                  datetime(6) not null,
  data_atualizacao              datetime(6) not null,
  constraint pk_disparos_alarmes primary key (id)
);

alter table disparos_alarmes add constraint fk_disparos_alarmes_usuarios foreign key (usuario_id) references usuarios (id) on delete restrict on update restrict;

# --- !Downs

drop table disparos_alarmes;
