CREATE TABLE IF NOT EXISTS client (
	id BIGSERIAL PRIMARY KEY NOT NULL,
	date_of_birth date NOT NULL,
	"name" varchar(255) NOT NULL,
	"password" varchar(500) NOT NULL
);

CREATE TABLE IF NOT EXISTS email_data (
	id BIGSERIAL PRIMARY KEY NOT NULL,
	mail varchar(255) NULL,
	client_id int8 NULL
);

ALTER TABLE email_data ADD CONSTRAINT email_client FOREIGN KEY (client_id) REFERENCES client(id);

CREATE TABLE IF NOT EXISTS phone_data (
	id BIGSERIAL PRIMARY KEY NOT NULL,
	phone varchar(255) NULL,
	client_id int8 NULL
);

ALTER TABLE phone_data ADD CONSTRAINT phone_client FOREIGN KEY (client_id) REFERENCES client(id);

CREATE TABLE IF NOT EXISTS account (
	id BIGSERIAL PRIMARY KEY NOT NULL,
    balance_initial numeric(19,2) NULL,
	balance numeric(19,2) NULL,
	client_id int8 NULL
);

ALTER TABLE account ADD CONSTRAINT account_client FOREIGN KEY (client_id) REFERENCES client(id);
