INSERT INTO client (date_of_birth, name, password) VALUES(TO_DATE('01.01.2000', 'DD.MM.YYYY'), 'Ivan Ivanov', 'passIvanov');
INSERT INTO account (client_id, balance, balance_initial) VALUES(1, 12.26, 12.26);
INSERT INTO phone_data (client_id, phone) VALUES( 1, '79207865432');
INSERT INTO email_data (client_id, mail) VALUES(1, 'test@mail.com');

INSERT INTO client (date_of_birth, name, password) VALUES(TO_DATE('02.02.2000', 'DD.MM.YYYY'), 'Pavel Pavlov', 'passPavlov');
INSERT INTO account (client_id, balance, balance_initial) VALUES(2, 14.35, 14.35);
INSERT INTO phone_data (client_id, phone) VALUES( 2,  '79207865433');
INSERT INTO email_data (client_id, mail) VALUES(2, 'test@yahoo.com');

INSERT INTO client (date_of_birth, name, password) VALUES(TO_DATE('03.03.2000', 'DD.MM.YYYY'), 'Timofey Timofeyev', 'passTimofeyev');
INSERT INTO account (client_id, balance, balance_initial) VALUES(3, 33.60, 33.60);
INSERT INTO phone_data (client_id, phone) VALUES( 3, '79207865434');
INSERT INTO email_data (client_id, mail) VALUES(3, 'test@gmail.com');