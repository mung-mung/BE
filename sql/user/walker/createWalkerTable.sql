DROP TABLE IF EXISTS walker CASCADE;
CREATE TABLE walker (
                       ID INT PRIMARY KEY,
                       FOREIGN KEY (ID) REFERENCES user_account(ID) ON DELETE CASCADE
);