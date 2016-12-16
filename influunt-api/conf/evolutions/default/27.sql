# --- !Ups
alter table controladores add column `exclusivo_para_teste` tinyint(1) NOT NULL DEFAULT '0';



# --- !Downs
alter table controladores drop column `exclusivo_para_teste`;

