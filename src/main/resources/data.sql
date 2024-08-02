INSERT INTO bank_accounts (creation_date, identical_number)
VALUES ('2024-01-01 00:00:00', '1111');

INSERT INTO user_data (first_name, last_name, passport_type, passportid, birthday)
VALUES ('Nikita', 'Novic', 'F', '1135', '1990-01-01 00:00:00');

INSERT INTO users (username, password, bank_account_id, personal_info_id)
VALUES ('nikita', '$2a$10$WzEUFP0G6Ht9I0c5NJunGOOHeCc96Z5DT7cJ2Lnrc4sMB0KXWbnyGO', 1, 1);

INSERT INTO user_role (user_id, roles)
VALUES (1, 1);

INSERT INTO user_emails (user_id, emails)
VALUES (1, 'efuirefwiu@gmail.com');

INSERT INTO user_phones (user_id, phones)
VALUES (1, '32146193');

