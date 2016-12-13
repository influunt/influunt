# --- !Ups

alter table controladores_fisicos add column `marca` varchar(255);
alter table controladores_fisicos add column `modelo` varchar(255);


# --- !Downs

alter table controladores_fisicos drop column `marca`;
alter table controladores_fisicos drop column `modelo`;
