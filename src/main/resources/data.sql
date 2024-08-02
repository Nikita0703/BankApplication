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

INSERT INTO card (balance, card_holder_name, card_number, cvv, expiration_date, is_active, bank_account_id)
VALUES
    (1000, 'Novic', '1234', '123', '2025-12-31 00:00:00', TRUE, 1);

INSERT INTO deposite (activation_code, interest_rate, is_active, sum, term, bank_account_id)
VALUES
    ('1111', 5, TRUE, 100, 12, 1);




INSERT INTO bank_accounts (creation_date, identical_number)
VALUES ('2023-01-01 00:00:00', '1112');

INSERT INTO user_data (first_name, last_name, passport_type, passportid, birthday)
VALUES ('Artmem', 'Ivanov', 'F', '1134', '1999-01-01 00:00:00');

INSERT INTO users (username, password, bank_account_id, personal_info_id)
VALUES ('artem', '$2a$10$WzEUFP0G6Ht9I0c5NJunGOOHeCc96Z5DT7cJ2Lnrc4sMB0KXbnyGO', 2, 2);

INSERT INTO user_role (user_id, roles)
VALUES (2, 0);

INSERT INTO user_emails (user_id, emails)
VALUES (2, 'efuirefrewiu@gmail.com');

INSERT INTO user_phones (user_id, phones)
VALUES (2, '3254146193');

INSERT INTO card (balance, card_holder_name, card_number, cvv, expiration_date, is_active, bank_account_id)
VALUES
    (1000, 'Ivanov', '1444', '113', '2025-10-31 00:00:00', TRUE, 2);

INSERT INTO deposite (activation_code, interest_rate, is_active, sum, term, bank_account_id)
VALUES
    ('1111', 5, TRUE, 100, 12, 2);