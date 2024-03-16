drop table if exists dogs CASCADE;
CREATE TABLE dogs (
                       ID_DOG SERIAL,
                       DOG_NAME VARCHAR(20) NOT NULL,
                       OWNER_ID INT NOT NULL,
                       OWNER_EMAIL VARCHAR(255) NOT NULL,
                       WALKER_ID INT NOT NULL,
                       WALKER_EMAIL VARCHAR(255),
                       CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       FOREIGN KEY (OWNER_ID, OWNER_EMAIL) REFERENCES USERS(ID, EMAIL),
                       FOREIGN KEY (WALKER_ID, WALKER_EMAIL) REFERENCES USERS(ID, EMAIL),
                       PRIMARY KEY (ID_DOG)
);
