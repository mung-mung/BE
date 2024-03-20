DROP TABLE IF EXISTS owning CASCADE;
CREATE TABLE owning (
                         ID SERIAL,
                         OWNER_ID INT NOT NULL,
                         DOG_ID INT NOT NULL,
                         FOREIGN KEY (OWNER_ID) REFERENCES owner(ID) ON DELETE CASCADE,
                         FOREIGN KEY (DOG_ID) REFERENCES dog(ID) ON DELETE CASCADE,
                         CREATED_AT TIMESTAMP,
                         UPDATED_AT TIMESTAMP,
                         PRIMARY KEY (ID)
);