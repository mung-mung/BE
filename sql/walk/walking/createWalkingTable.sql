DROP TABLE IF EXISTS walking CASCADE;
CREATE TABLE walking (
                           ID SERIAL,
                           WALKER_ID INT NOT NULL,
                           DOG_ID INT NOT NULL,
                           FOREIGN KEY (WALKER_ID) REFERENCES walker(ID) ON DELETE CASCADE,
                           FOREIGN KEY (DOG_ID) REFERENCES dog(ID) ON DELETE CASCADE,
                           CREATED_AT TIMESTAMP,
                           UPDATED_AT TIMESTAMP,
                           PRIMARY KEY (ID)
);