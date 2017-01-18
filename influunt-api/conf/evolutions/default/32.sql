# --- !Ups
ALTER TABLE agrupamentos
  CHANGE COLUMN dia_da_semana dia_da_semana VARCHAR(16) NULL,
  CHANGE COLUMN horario horario TIME NULL;

# --- !Downs
ALTER TABLE agrupamentos
  CHANGE COLUMN dia_da_semana dia_da_semana VARCHAR(16) NOT NULL default '',
  CHANGE COLUMN horario horario TIME NOt NULL default '00:00:00';
