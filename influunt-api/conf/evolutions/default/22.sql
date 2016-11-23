#-- !Ups
ALTER TABLE controladores ADD COLUMN central_private_key varchar(65535);
ALTER TABLE controladores ADD COLUMN central_public_key varchar(65535);
ALTER TABLE controladores ADD COLUMN controlador_public_key varchar(65535);
ALTER TABLE controladores ADD COLUMN controlador_private_key varchar(65535);

# --- !Downs
ALTER TABLE controladores DROP COLUMN central_private_key;
ALTER TABLE controladores DROP COLUMN central_public_key;
ALTER TABLE controladores DROP COLUMN controlador_public_key;
ALTER TABLE controladores DROP COLUMN controlador_private_key;
