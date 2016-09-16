# --- !Ups

ALTER TABLE usuarios ADD COLUMN reset_password_token VARCHAR (40);
ALTER TABLE usuarios ADD COLUMN password_token_expiration datetime(6);

# --- !Downs

ALTER TABLE usuarios DROP COLUMN reset_password_token;
ALTER TABLE usuarios DROP COLUMN password_token_expiration;

