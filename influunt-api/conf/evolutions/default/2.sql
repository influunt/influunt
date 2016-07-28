# --- !Ups
ALTER TABLE controladores
ADD sequencia integer not null;

# --- !Downs

ALTER TABLE controladores
DROP COLUMN sequencia;

