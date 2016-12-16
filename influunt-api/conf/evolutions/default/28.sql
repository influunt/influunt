# --- !Ups

alter table controladores_fisicos add column `password` varchar(255);
alter table controladores_fisicos add column `password_hash` varchar(255);


# --- !Downs

alter table controladores_fisicos drop column `password`;
alter table controladores_fisicos drop column `password_hash`;

