DROP TABLE IF EXISTS owner CASCADE;
CREATE TABLE owner (
                       ID INT PRIMARY KEY,
                       FOREIGN KEY (ID) REFERENCES user_account(ID) ON DELETE CASCADE
);