DROP TABLE IF EXISTS walker CASCADE;
CREATE TABLE walker (
                        ID SERIAL,
                        ACCOUNT_ID INT NOT NULL,
                        FOREIGN KEY (ACCOUNT_ID) REFERENCES user_account(ID) ON DELETE CASCADE,
                        PRIMARY KEY (ID)
);