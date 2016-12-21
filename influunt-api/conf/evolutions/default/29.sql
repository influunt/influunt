# --- !Ups
alter table estagios_planos drop foreign key fk_estagios_planos_estagio_que_recebe_estagio_dispensavel_3;
alter table estagios_planos add constraint fk_estagios_planos_estagio_que_recebe_estagio_dispensavel_3 foreign key (estagio_que_recebe_estagio_dispensavel_id) references estagios_planos (id) on delete set null on update restrict;

# --- !Downs
alter table estagios_planos drop foreign key fk_estagios_planos_estagio_que_recebe_estagio_dispensavel_3;
alter table estagios_planos add constraint fk_estagios_planos_estagio_que_recebe_estagio_dispensavel_3 foreign key (estagio_que_recebe_estagio_dispensavel_id) references estagios_planos (id) on delete restrict on update restrict;

