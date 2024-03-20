drop table if exists dogs CASCADE;
CREATE TABLE dogs (
                      ID SERIAL,
                      NAME VARCHAR(20) NOT NULL,
                      OWNER_ID INT NOT NULL,
                      WALKER_ID INT,
                      CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      FOREIGN KEY (OWNER_ID) REFERENCES USERS(ID),
                      FOREIGN KEY (WALKER_ID) REFERENCES USERS(ID),
                      PRIMARY KEY (ID)
);
