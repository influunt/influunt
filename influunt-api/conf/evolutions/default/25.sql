# --- !Ups

alter table controladores_fisicos add column `status_device` integer;


# --- !Downs

alter table controladores_fisicos drop column `status_device`;
