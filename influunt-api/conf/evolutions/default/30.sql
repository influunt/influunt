# --- !Ups
alter table controladores_fisicos add (
  `fabricante_hardware` mediumtext,
  `modelo_hardware` mediumtext,
  `versao_firmware_hardware` mediumtext,
  `atualizacao_versao_hardware` DATETIME
);

# --- !Downs

alter table controladores_fisicos  drop column `fabricante_hardware`;
alter table controladores_fisicos  drop column `modelo_hardware`;
alter table controladores_fisicos  drop column `versao_firmware_hardware`;
alter table controladores_fisicos  drop column `atualizacao_versao_hardware`;
