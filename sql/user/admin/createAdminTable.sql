DROP TABLE IF EXISTS admin CASCADE;
CREATE TABLE admin (
                       ID INT PRIMARY KEY,
                       FOREIGN KEY (ID) REFERENCES user_account(ID) ON DELETE CASCADE
);