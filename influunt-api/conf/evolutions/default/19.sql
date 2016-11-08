# --- !Ups
ALTER TABLE transicoes ADD COLUMN modo_intermitente_ou_apagado TINYINT(1) NOT NULL DEFAULT 0 AFTER destroy;


# --- !Downs
ALTER TABLE transicoes DROP COLUMN modo_intermitente_ou_apagado;
