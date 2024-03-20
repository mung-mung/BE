DROP TABLE IF EXISTS owner CASCADE;
CREATE TABLE owner (
                        ID SERIAL,
                        ACCOUNT_ID INT NOT NULL,
                        FOREIGN KEY (ACCOUNT_ID) REFERENCES user_account(ID) ON DELETE CASCADE,
                        CREATED_AT TIMESTAMP,
                        UPDATED_AT TIMESTAMP,
                        PRIMARY KEY (ID)
);