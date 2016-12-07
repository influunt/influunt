# --- !Ups

alter table controladores_fisicos add (
  `central_private_key` mediumtext,
  `central_public_key` mediumtext,
  `controlador_public_key` mediumtext,
  `controlador_private_key` mediumtext,
  `controlador_sincronizado_id` varchar(40) DEFAULT NULL
);

alter table controladores add (
    `sincronizado` tinyint(1)
);

alter table controladores drop column `central_private_key`;
alter table controladores drop column `central_public_key`;
alter table controladores drop column `controlador_public_key`;
alter table controladores drop column `controlador_private_key`;


# --- !Downs

alter table controladores add (
  `central_private_key` mediumtext,
  `central_public_key` mediumtext,
  `controlador_public_key` mediumtext,
  `controlador_private_key` mediumtext
);

alter table controladores_fisicos  drop column `central_private_key`;
alter table controladores_fisicos  drop column `central_public_key`;
alter table controladores_fisicos  drop column `controlador_public_key`;
alter table controladores_fisicos  drop column `controlador_private_key`;
alter table controladores_fisicos drop column `controlador_sincronizado_id`;
alter table controladores drop column `sincronizado`;
